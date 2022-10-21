package nl.dw.currency.controller;

import nl.dw.currency.model.ResponseRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class WareHouseController {
    @Value("${services.crawler.baseUrl}")
    private String crawlerBaseURL;
    @Value("${services.crawler.crawl}")
    private String crawlServiceURL;
    @Value("${services.crawler.all-data}")
    private String allDataServiceURL;

    @GetMapping("/getDataFromStaging")
    public ResponseEntity<ResponseRequest> getDataFromStaging () {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(crawlerBaseURL+allDataServiceURL, ResponseRequest.class);
    }
}
