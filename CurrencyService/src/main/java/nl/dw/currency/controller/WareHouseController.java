package nl.dw.currency.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import nl.dw.currency.model.*;
import nl.dw.currency.repository.IBankRepository;
import nl.dw.currency.repository.ICurrencyRepository;
import nl.dw.currency.repository.IExchangeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class WareHouseController {
	@Value("${services.crawler.baseUrl}")
	private String crawlerBaseURL;
	@Value("${services.crawler.crawl}")
	private String crawlServiceURL;
	@Value("${services.crawler.all-data}")
	private String allDataServiceURL;
	
	@Value("${services.zoneId}")
	private String zoneId;
	private final IBankRepository bankRepository;
	private final ICurrencyRepository currencyRepository;
	private final IExchangeRepository exchangeRepository;
	
	@GetMapping("/getDataFromStaging")
	public ResponseEntity<ResponseRequest<StagingResponse>> getDataFromStaging () {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ResponseRequest> response = restTemplate.getForEntity(crawlerBaseURL + allDataServiceURL,
				ResponseRequest.class);
		StagingResponse stagingResponse = new StagingResponse();
		stagingResponse.setTotalCompleted(0);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			ResponseRequest body = response.getBody();
			if (body.getData() != null) {
				Predicate<DataCrawler> bankNotNull = dataCrawler -> !dataCrawler.getBank().isEmpty();
				Predicate<DataCrawler> symbolCurrencyNotNull =
						dataCrawler -> !dataCrawler.getCurrencySymbol().isEmpty();
				ObjectMapper mapper = new ObjectMapper();
				mapper.registerModule(new JavaTimeModule());
				List<DataCrawler> dataCrawlers = mapper.convertValue(body.getData(), new TypeReference<>() {});
				stagingResponse.setRows(dataCrawlers.size());
				
				dataCrawlers.stream().filter(bankNotNull.and(symbolCurrencyNotNull)).forEach(dataCrawler -> {
					String bankName = dataCrawler.getBank();
					String symbolCurrency = dataCrawler.getCurrencySymbol();
					Exchange exchange = Exchange.builder().getDate(dataCrawler.getGetDate()).buyCash(
							dataCrawler.getBuyCash()).buyTransfer(dataCrawler.getBuyTransfer()).price(
							dataCrawler.getPrice()).updateDate(dataCrawler.getUpdateDate()).urlSource(
							dataCrawler.getUrlSource()).build();
					Bank bank = bankRepository.findBankByName(bankName).orElse(Bank.builder().name(bankName).build());
					if (bank.getId() == null) {
						Bank newBank = bankRepository.save(bank);
						bank = newBank;
					}
					exchange.setBank(bank);
					Currency currency = currencyRepository.findCurrencyBySymbol(symbolCurrency).orElse(
							Currency.builder().symbol(symbolCurrency).name(dataCrawler.getCurrencyName()).build());
					if (currency.getId() == null) {
						Currency newCurrency = currencyRepository.save(currency);
						currency = newCurrency;
					}
					exchange.setCurrency(currency);
					exchangeRepository.save(exchange);
					int completed = stagingResponse.getTotalCompleted() + 1;
					stagingResponse.setTotalCompleted(completed);
				});
				LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(zoneId));
				stagingResponse.setCompletedTime(localDateTime);
			}
		}
		ResponseRequest<StagingResponse> responseRequest = new ResponseRequest<>();
		responseRequest.setData(stagingResponse);
		responseRequest.setStatus(ResponseRequest.ResponseStatus.SUCCESS);
		return new ResponseEntity<>(responseRequest, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<ResponseRequest<List<Exchange>>> getAllExchange () {
		ResponseRequest<List<Exchange>> responseRequest = new ResponseRequest<>();
		Iterator<Exchange> exchangeIterator = exchangeRepository.findAll().iterator();
		List<Exchange> exchanges = new ArrayList<>();
		exchangeIterator.forEachRemaining(exchanges::add);
		responseRequest.setData(exchanges);
		responseRequest.setStatus(ResponseRequest.ResponseStatus.SUCCESS);
		return new ResponseEntity<>(responseRequest, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{exchangeId}", method = RequestMethod.GET)
	public ResponseEntity<ResponseRequest<Exchange>> getAllExchange (@PathVariable @NotNull @DecimalMin("0") Long exchangeId) {
		ResponseRequest<Exchange> responseRequest = new ResponseRequest<>();
		try {
			Exchange exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new EntityNotFoundException(
					"Exchange id " + exchangeId +
					" not exist"));
			responseRequest.setData(exchange);
			responseRequest.setStatus(ResponseRequest.ResponseStatus.SUCCESS);
		}catch (Exception ex) {
			responseRequest.setStatus(ResponseRequest.ResponseStatus.FAILED);
			responseRequest.setMessage(ex.getMessage());
		}
		return new ResponseEntity<>(responseRequest, HttpStatus.OK);
	}
}
