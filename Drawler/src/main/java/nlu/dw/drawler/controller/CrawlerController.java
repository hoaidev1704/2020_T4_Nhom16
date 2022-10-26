package nlu.dw.drawler.controller;

import lombok.RequiredArgsConstructor;
import nlu.dw.drawler.model.DataRecord;
import nlu.dw.drawler.model.RecordAudit;
import nlu.dw.drawler.model.ResponseRequest;
import nlu.dw.drawler.repository.IAuditRepository;
import nlu.dw.drawler.repository.IDataRepository;
import nlu.dw.drawler.service.FileHelper;
import nlu.dw.drawler.service.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CrawlerController {
    private final ICrawlerService crawlerService;
    private final FileHelper fileHelper;
    private final IDataRepository iDataRepository;
    private final IAuditRepository auditRepository;
    @GetMapping("/getAllExchangeData")
    public ResponseEntity<ResponseRequest> getAllExchangeData() {
        Iterator<DataRecord> iterator = iDataRepository.findAll().iterator();
        return new ResponseEntity<ResponseRequest>(ResponseRequest.builder().status(ResponseRequest.ResponseStatus.SUCCESS).data(iterator).build(),
                HttpStatus.OK);
    }
    @GetMapping("/crawl")
    public ResponseEntity<ResponseRequest> crawlData() {
        try {
            List<DataRecord> theBankData = crawlerService.getDataFromTheBankPage();
            List<DataRecord> bankData = crawlerService.getDataFromBankingPage();

            if(!theBankData.isEmpty()){
                List<DataRecord> resultTheBankData =  new ArrayList<>();
                iDataRepository.saveAll(theBankData).iterator().forEachRemaining(resultTheBankData::add);
                RecordAudit theBankAudit = fileHelper.getAuditInfo(resultTheBankData);
                if(theBankAudit != null) {
                    auditRepository.save(theBankAudit);
                }
            }

            if(!bankData.isEmpty()){
                List<DataRecord> resultBankData =  new ArrayList<>();
                iDataRepository.saveAll(bankData).iterator().forEachRemaining(resultBankData::add);
                RecordAudit bankAudit = fileHelper.getAuditInfo(resultBankData);;
                if(bankAudit != null) {
                    auditRepository.save(bankAudit);
                }
            }

        }catch (Exception e) {
            RecordAudit audit = RecordAudit.builder().recordNumber(0).startDate(LocalDateTime.now()).endDate(LocalDateTime.now())
                    .status(RecordAudit.STATUS.FAIL).build();
            auditRepository.save(audit);
            return new ResponseEntity<>(ResponseRequest.builder().status(ResponseRequest.ResponseStatus.FAILED)
                    .message(e.getMessage())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseRequest.builder().status(ResponseRequest.ResponseStatus.SUCCESS).build()
                ,HttpStatus.OK);
    }
}
