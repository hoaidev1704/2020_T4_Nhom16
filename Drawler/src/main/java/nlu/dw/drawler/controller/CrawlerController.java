package nlu.dw.drawler.controller;

import lombok.RequiredArgsConstructor;
import nlu.dw.drawler.model.DataRecord;
import nlu.dw.drawler.service.FileHelper;
import nlu.dw.drawler.service.ICrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlerController {
    private final ICrawlerService crawlerService;
    private final FileHelper fileHelper;
    @GetMapping("/crawl")
    public ResponseEntity<String> crawlData() {
        try {
            List<DataRecord> theBankData = crawlerService.getDataFromTheBankPage();
            List<DataRecord> bankData = crawlerService.getDataFromBankingPage();
            fileHelper.writeRecordData(theBankData);
            fileHelper.writeRecordData(bankData);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
