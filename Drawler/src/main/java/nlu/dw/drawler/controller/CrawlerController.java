package nlu.dw.drawler.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {
    @GetMapping("/crawl")
    public ResponseEntity<String> crawlData() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
