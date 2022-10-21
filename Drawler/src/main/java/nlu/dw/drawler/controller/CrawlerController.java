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
    private final IAuditRepository iAuditRepository;
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
                RecordAudit theBankAudit = fileHelper.writeRecordData(resultTheBankData);
                if(theBankAudit != null) {
                    if(!iAuditRepository.existsRecordAuditByFileName(theBankAudit.getFileName())){
                        fileHelper.writeAudit(iAuditRepository.save(theBankAudit));
                    }
                }
            }

            if(!bankData.isEmpty()){
                List<DataRecord> resultBankData =  new ArrayList<>();
                iDataRepository.saveAll(bankData).iterator().forEachRemaining(resultBankData::add);
                RecordAudit bankAudit = fileHelper.writeRecordData(resultBankData);;
                if(bankAudit != null) {
                    if(!iAuditRepository.existsRecordAuditByFileName(bankAudit.getFileName())){
                        fileHelper.writeAudit(iAuditRepository.save(bankAudit));
                    }
                }
            }

        }catch (Exception e) {
            return new ResponseEntity<>(ResponseRequest.builder().status(ResponseRequest.ResponseStatus.FAILED)
                    .message(e.getMessage())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseRequest.builder().status(ResponseRequest.ResponseStatus.SUCCESS).build()
                ,HttpStatus.OK);
    }
}
