package nlu.dw.drawler.model;

import lombok.Getter;
import lombok.Setter;
import nlu.dw.drawler.service.FileHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
public class Crawler {

    private String fileName, getDate="", getTime="";
    private List<DataRecord> data;
    private RecordAudit recordAudit;
    public static final String URL_BANK_DOMAIN = "https://ngan-hang.com";
    public static final String URL_BANK_EXCHANGE_RATE = URL_BANK_DOMAIN + "/ty-gia";
    public static final String URL_THE_BANK_DOMAIN = "https://thebank.vn";
    public static final String URL_THE_BANK_EXCHANGE_RATE = URL_THE_BANK_DOMAIN + "/cong-cu/tinh-ty-gia-ngoai-te/ty-gia-bidv.html";


}
