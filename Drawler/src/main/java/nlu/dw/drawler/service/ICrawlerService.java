package nlu.dw.drawler.service;

import nlu.dw.drawler.model.DataRecord;

import java.io.IOException;
import java.util.List;

public interface ICrawlerService {
    List<DataRecord> getDataFromTheBankPage() throws IOException;
    List<DataRecord> getDataFromBankingPage() throws IOException;
}
