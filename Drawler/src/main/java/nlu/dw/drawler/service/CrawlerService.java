package nlu.dw.drawler.service;

import nlu.dw.drawler.model.Crawler;
import nlu.dw.drawler.model.DataRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CrawlerService implements ICrawlerService{
    @Override
    public List<DataRecord> getDataFromTheBankPage () throws IOException {
        List<DataRecord> data = new ArrayList<>();
        Document doc2 = Jsoup.connect(Crawler.URL_THE_BANK_EXCHANGE_RATE).get();
        // lay ngay gio cap nhat
        String dateTimeUpdate =convertDateFormat(doc2.getElementsByClass("fluid1").select("p").get(2).text().split(" ")[8]);

        // lay url cua cac trang chi tiet tung ngan hang
        Elements elBanks = doc2.select("div[class=company_other]").select("ul").first().select("li a");
        TreeMap<String, String> bankUrls = new TreeMap<>();
        for (int i = 0; i < elBanks.size(); i++) {
            String[] arrTemp = elBanks.get(i).text().split(" ");
            String bankName = "";
            for (int j = 2; j < arrTemp.length; j++) {
                bankName = bankName + arrTemp[j] + " ";
            }
            bankUrls.put(bankName, elBanks.get(i).attr("href"));
        }

        Set<Map.Entry<String, String>> entrySet = bankUrls.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);

        // lay du lieu tu cac trang chi tiet
        for (int i = 0; i < entryList.size(); i++) {
            String detailUrl = Crawler.URL_THE_BANK_DOMAIN + entryList.get(i).getValue();
            try {
                Document detailDoc2 = Jsoup.connect(detailUrl).get();
                String bankName = entryList.get(i).getKey();

                // lay tung dong ngoai te trong bang
                Elements dataLine = detailDoc2.select("tbody tr");
                for (int j = 0; j < dataLine.size(); j++) {
                    Elements dataCell = dataLine.get(j).select("td");

                    String symbol = dataCell.get(0).text(), currentName = dataCell.get(1).text();
                    double buyCash = Double.parseDouble(dataCell.get(2).text().trim().replaceAll(",", "")),
                            buyTransfer = Double.parseDouble(dataCell.get(3).text().trim().replaceAll(",", "")),
                            price = Double.parseDouble(dataCell.get(4).text().trim().replaceAll(",", ""));
                    LocalDateTime now = LocalDateTime.now();
                    DataRecord r = new DataRecord(UUID.randomUUID(), now, now ,dateTimeUpdate, Crawler.URL_THE_BANK_DOMAIN, bankName, currentName, symbol, buyCash,
                            buyTransfer, price);
                    data.add(r);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return data;
    }

    @Override
    public List<DataRecord> getDataFromBankingPage () throws IOException {
        List<DataRecord> data = new ArrayList<>();
        Document doc = Jsoup.connect(Crawler.URL_BANK_EXCHANGE_RATE).get();
        String title = doc.title();
        System.out.println("Getting data from nganhang.com page...");
        // lay ngay gio cap nhat
        Elements dateTimeUpdateElement = doc.getElementsByClass("note").select("p");
        String[] dateTimeUpdateLine = dateTimeUpdateElement.get(2).text().split(" ");
        String dateUpdate =convertDateFormat(dateTimeUpdateLine[7]);

        // lay url cua cac trang chi tiet tung ngan hang
        Elements elBanks = doc.select("ul[class=list-style]").last().select("li a");
        TreeMap<String, String> bankUrls = new TreeMap<>();
        for (int i = 0; i < elBanks.size(); i++) {
            bankUrls.put(elBanks.get(i).text().split(" ")[2], elBanks.get(i).attr("href"));
        }

        Set<Map.Entry<String, String>> entrySet = bankUrls.entrySet();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(entrySet);

        // lay du lieu tu cac trang chi tiet
        for (int i = 0; i < entryList.size(); i++) {
            String detailUrl = Crawler.URL_BANK_DOMAIN + entryList.get(i).getValue();
            Document detailDoc = Jsoup.connect(detailUrl).get();
            Elements dataRows = detailDoc.getElementsByClass("table-bordered").select("tbody tr");

            for (int j = 2; j < dataRows.size(); j++) {
                Elements tdTags = dataRows.get(j).select("td");
                //---------------------------------------------------------
                String bankName = entryList.get(i).getKey(), currentName = tdTags.get(0).text(),
                        symbol = tdTags.get(1).select("b a").text();
                LocalDateTime now = LocalDateTime.now();
                DataRecord dataRecord = DataRecord.builder()
                        .id(UUID.randomUUID())
                        .getDate(now)
                        .updateDate(now)
                        .bank(bankName)
                        .urlSource(detailUrl)
                        .build();
                convertCashFromPage(dataRecord, tdTags);
                //---------------------------------------------------------
            }
        }
        return data;
    }

    private void convertCashFromPage (DataRecord dataRecord, Elements dataCell) {
        double buyCash = Double.parseDouble(dataCell.get(2).text().trim().replaceAll(",", "")),
                buyTransfer = Double.parseDouble(dataCell.get(3).text().trim().replaceAll(",", "")),
                price = Double.parseDouble(dataCell.get(4).text().trim().replaceAll(",", ""));
        dataRecord.setBuyCash(buyCash);
        dataRecord.setBuyTransfer(buyTransfer);
        dataRecord.setPrice(price);
    }

    private  String convertDateFormat(String date) {
        char[] cs = date.toCharArray();
        return ""+cs[6]+cs[7]+cs[8]+cs[9]+"-"+cs[3]+cs[4]+"-"+cs[0]+cs[1];
    }


}
