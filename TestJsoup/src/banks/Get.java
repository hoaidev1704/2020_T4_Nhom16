package banks;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Get {
	private String fileName, getDate="", getTime="";
	private List<DataRecord> data;
	private LogRecord logRecord;
	
	public Get() throws IOException {
		//lay thoi gian ex du lieu
		Date now = new Date();
		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat formatTime = new SimpleDateFormat("HH:mm");

		this.getDate = formatDate.format(now);
		this.getTime = formatTime.format(now);
		
		logRecord= new LogRecord(getDate,getTime,"",false);
		
		data = new ArrayList<>();
		writeFile();
		writeLog();
	}

	public List<DataRecord> getData() {
		return data;
	}

	public String getFileName() {
		return fileName;
	}

	// phuong thuc lay du lieu tu cac trang
	public void writeFile() throws IOException {
		getDataFromNganHangPage();
		getDataFromTheBankPage();

		System.out.println("Writing file...");
		
		for (DataRecord dataRecord : data) {
			System.out.println(dataRecord);
		}

		String date = data.get(0).getGetDate().replaceAll("/", "-");
		String time = data.get(0).getGetTime().replaceAll(":", "g");
		fileName = "data_" + date + "_" + time + ".csv";
		String filePath = ".\\csvFile\\" + fileName;

		FileWriter dataFile = new FileWriter(filePath);
		int id = 1;

		for (DataRecord r : data) {
			r.setId(id);
			id++;
			dataFile.write(r.getId() + "," + r.getGetDate() + "," + r.getGetTime() + "," + r.getUpdateDate() + ","
					+ r.getUrlSource() + "," + r.getBank() + "," + r.getCurrencyName() + "," + r.getCurrencySymbol()
					+ "," + r.getBuyCash() + "," + r.getBuyTransfer() + "," + r.getPrice() + "\n");
		}
		dataFile.close();
		System.out.println("SUCCESS");

	}

	// lay du lieu tu trang nganhang.com
	public void getDataFromNganHangPage() throws IOException {
		String link = "https://ngan-hang.com/ty-gia";
		Document doc = Jsoup.connect(link).get();
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
			String detailUrl = "https://ngan-hang.com" + entryList.get(i).getValue();
			Document detailDoc = Jsoup.connect(detailUrl).get();
			Elements dataRows = detailDoc.getElementsByClass("table-bordered").select("tbody tr");
			for (int j = 2; j < dataRows.size(); j++) {
				Elements tdTags = dataRows.get(j).select("td");
//---------------------------------------------------------
				String bankName = entryList.get(i).getKey(), currentName = tdTags.get(0).text(),
						symbol = tdTags.get(1).select("b a").text();
				double buyCash = Double.parseDouble(tdTags.get(2).text().trim().replaceAll(",", "")),
						buyTransfer = Double.parseDouble(tdTags.get(3).text().trim().replaceAll(",", "")),
						price = Double.parseDouble(tdTags.get(4).text().trim().replaceAll(",", ""));
				DataRecord line = new DataRecord(this.getDate, this.getTime, dateUpdate, detailUrl, bankName, currentName, symbol, buyCash, buyTransfer,
						price);
				data.add(line);
//---------------------------------------------------------
			}
		}
		System.out.println("SUCCESS");

	}

	public void getDataFromTheBankPage() throws IOException {
		String link = "https://thebank.vn/cong-cu/tinh-ty-gia-ngoai-te/ty-gia-bidv.html";
		Document doc2 = Jsoup.connect(link).get();
		String title = doc2.title();
		System.out.println("Getting data from thebank.com page...");

		// lay ngay gio cap nhat
		String dateTimeUpdate =convertDateFormat(doc2.getElementsByClass("fluid1").select("p").get(2).text().split(" ")[8]);
		System.out.println(dateTimeUpdate);

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
			String detailUrl = "https://thebank.vn" + entryList.get(i).getValue();
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
					DataRecord r = new DataRecord(this.getDate, this.getTime ,dateTimeUpdate, "https://thebank.vn", bankName, currentName, symbol, buyCash,
							buyTransfer, price);
					data.add(r);
				}
			} catch (Exception e) {
				continue;
			}
		}
		System.out.println("SUCCESS");

	}

	public String convertDateFormat(String date) {
		char[] cs = date.toCharArray();
		return ""+cs[6]+cs[7]+cs[8]+cs[9]+"-"+cs[3]+cs[4]+"-"+cs[0]+cs[1];
	}
	
	public void writeLog() throws IOException {
		logRecord.setFileName(fileName);
		logRecord.setStatus(true);
		FileWriter logWrite = new FileWriter(".\\csvFile\\log_file\\log-file2.csv");
		logWrite.write(logRecord.getGetDate()+","+logRecord.getGetTime()+","+logRecord.getFileName()+","+logRecord.getStatus()+"\n");
		logWrite.close();
	}

//	public static void main(String[] args) throws IOException {
//		new Get();
//	}
}
