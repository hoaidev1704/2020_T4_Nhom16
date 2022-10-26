package nlu.dw.drawler.service;

import nlu.dw.drawler.model.DataRecord;
import nlu.dw.drawler.model.RecordAudit;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Component
public class FileHelper {
    private static final String ROOT_CSV_FILE = "." + File.separatorChar + "csvFiles";
    private static final String ROOT_CSV_LOG_FILE = "." + File.separatorChar + "log_csvFiles";
    
    public RecordAudit getAuditInfo(List<DataRecord> recordList) {
        DataRecord firstRecord = recordList.get(0);
        DataRecord lastRecord = recordList.get(recordList.size()-1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fileName = "data_" + dtf.format(recordList.get(0).getGetDate()) + ".csv";
        StringBuilder content = new StringBuilder();
        for (DataRecord r : recordList) {
            content.append(r.getId().toString() + "," + dtf.format(r.getGetDate()) + "," + dtf.format(r.getUpdateDate()) + ","
                    + r.getUrlSource() + "," + r.getBank() + "," + r.getCurrencyName() + "," + r.getCurrencySymbol()
                    + "," + r.getBuyCash() + "," + r.getBuyTransfer() + "," + r.getPrice() + "\n");
        }
        RecordAudit recordAudit = RecordAudit.builder()
                .recordNumber(recordList.size())
                .fileName(fileName)
                .startDate(firstRecord.getGetDate())
                .endDate(lastRecord.getGetDate())
                .status(RecordAudit.STATUS.SUCESS)
                .content(content.toString())
                .build();
        return recordAudit;
    }
    
    public RecordAudit writeRecordData(RecordAudit recordAudit) throws IOException {
        if(recordAudit == null) return null;
        StringBuilder content = new StringBuilder();
        File theDir = new File(ROOT_CSV_FILE);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        FileWriter dataFile =
                new FileWriter(getCSVCurrentFolder().getAbsolutePath() + File.separatorChar + recordAudit.getFileName());
        dataFile.write(content.toString());
        dataFile.close();
        return recordAudit;
    }


    public void writeAudit(RecordAudit recordAudit) throws IOException {
        File rootAuditDir = new File(ROOT_CSV_LOG_FILE);
        if (!rootAuditDir.exists()){
            rootAuditDir.mkdirs();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fileName = dtf.format(recordAudit.getStartDate())+ "--" + dtf.format(recordAudit.getEndDate());
        FileWriter logWrite = null;
        try {
            logWrite = new FileWriter(getLogCSVCurrentFolder().getAbsolutePath() + File.separatorChar + fileName +".csv");
            logWrite.write(recordAudit.getId().toString()+","+recordAudit.getStartDate()+","+ recordAudit.getEndDate()+","+ recordAudit.getFileName()+","+ recordAudit.getStatus().name()+","+ recordAudit.getRecordNumber()+"\n");
        } catch (Exception io) {
            throw new IOException(io);
        } finally {
            if(logWrite != null) {
                logWrite.flush();
                logWrite.close();
            }
        }


    }

    public static File getCSVCurrentFolder() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String pathFolder = ROOT_CSV_FILE + File.separatorChar + dtf.format(LocalDateTime.now());
        File theDir = new File(pathFolder);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        return theDir;
    }

    public static File getLogCSVCurrentFolder() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String pathFolder = ROOT_CSV_LOG_FILE + File.separatorChar + "Log_" + dtf.format(LocalDateTime.now());
        File theDir = new File(pathFolder);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        return theDir;
    }


}
