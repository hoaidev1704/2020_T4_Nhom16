package nlu.dw.drawler.service;

import nlu.dw.drawler.model.DataRecord;
import nlu.dw.drawler.model.RecordAudit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
public class FileHelper {
    public void writeRecordData(List<DataRecord> recordList) throws IOException {
        if(recordList.isEmpty()) return;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fileName = "data_" + dtf.format(recordList.get(0).getGetDate()) + ".csv";
        String pathFolder = "."+File.separatorChar+"csvFile";
        File theDir = new File(pathFolder);
        if (!theDir.exists()){
            theDir.mkdirs();
        }

        FileWriter dataFile = new FileWriter(pathFolder + File.separatorChar + fileName);
        DataRecord firstRecord = recordList.get(0);
        DataRecord lastRecord = recordList.get(recordList.size()-1);
        for (DataRecord r : recordList) {
            r.setId(UUID.randomUUID());
            dataFile.write(r.getId().toString() + "," + dtf.format(r.getGetDate()) + "," + dtf.format(r.getUpdateDate()) + ","
                    + r.getUrlSource() + "," + r.getBank() + "," + r.getCurrencyName() + "," + r.getCurrencySymbol()
                    + "," + r.getBuyCash() + "," + r.getBuyTransfer() + "," + r.getPrice() + "\n");
        }
        RecordAudit recordAudit = RecordAudit.builder().id(UUID.randomUUID().toString())
                .recordNumber(recordList.size())
                .fileName(fileName)
                .startDate(firstRecord.getGetDate())
                .endDate(lastRecord.getGetDate())
                .build();
        writeAudit(recordAudit);
        dataFile.close();
    }


    public void writeAudit(RecordAudit recordAudit) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fileName = dtf.format(recordAudit.getStartDate())+ "--" + dtf.format(recordAudit.getEndDate());
        String pathFolder = "."+File.separatorChar+"log_csvFile";
        File theDir = new File(pathFolder);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        FileWriter logWrite = new FileWriter(pathFolder + File.separatorChar + fileName +".csv");
        logWrite.write(recordAudit.getStartDate()+","+ recordAudit.getEndDate()+","+ recordAudit.getFileName()+","+ recordAudit.isStatus()+","+ recordAudit.getRecordNumber()+"\n");
        logWrite.close();
    }

}
