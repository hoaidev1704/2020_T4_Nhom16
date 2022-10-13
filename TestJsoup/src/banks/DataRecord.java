package banks;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DataRecord {
	private int id;
	private String getDate, updateDate;
	private String getTime;
	private String urlSource, currency, bank, currencySymbol, currencyName;
	private double buyCash, buyTransfer, price;

	public DataRecord(String getDate, String getTime, String updateDate, String urlSource, String bank,
			String currencyName, String currencySymbol, double buyCash, double buyTransfer, double price) {
		super();

//		Date now = new Date();
//		DateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
//		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
//
//		DateFormat formatTime = new SimpleDateFormat("HH:mm");

		this.getDate = getDate;
		this.getTime = getTime;
		this.updateDate = updateDate;
		this.urlSource = urlSource;
		this.currencyName = currencyName;
		this.currencySymbol = currencySymbol;
		this.buyCash = buyCash;
		this.buyTransfer = buyTransfer;
		this.price = price;
		this.bank = bank;
	}

//	public DataRecord(String updateDate, String urlSource, String bank, String currencyName, String currencySymbol,
//			double priceCash, double buyTransfer, double price) {
//		super();
//
//		Date now = new Date();
//		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
//
//		DateFormat formatTime = new SimpleDateFormat("HH:mm");
//
//		this.getDate = formatDate.format(now);
//		this.getTime = formatTime.format(now);
//
//		this.updateDate = updateDate;
//		this.urlSource = urlSource;
//		this.currencyName = currencyName;
//		this.currencySymbol = currencySymbol;
//		this.buyCash = buyCash;
//		this.buyTransfer = buyTransfer;
//		this.price = price;
//		this.bank = bank;
//	}

	public DataRecord() {
		Date now = new Date();
		DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat formatTime = new SimpleDateFormat("HH:mm");
		System.out.println(formatTime.format(now).toString());
	}

	@Override
	public String toString() {
		return "Record [ngayLay=" + getDate + ", gioLay=" + getTime + ", ngayCapNhat=" + updateDate + ", urlNguon="
				+ urlSource + ", tenNgoaiTe=" + currencyName + ", kyHieuNgoaiTe=" + currencySymbol + ", muaTienMat="
				+ buyCash + ", muaChuyenKhoan=" + buyTransfer + ", giaBan=" + price + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getUrlSource() {
		return urlSource;
	}

	public void setUrlSource(String urlSource) {
		this.urlSource = urlSource;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCurrencySymbol() {
		return currencySymbol;
	}

	public void setCurrencySymbol(String currencySymbol) {
		this.currencySymbol = currencySymbol;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public double getBuyCash() {
		return buyCash;
	}

	public void setBuyCash(double buyCash) {
		this.buyCash = buyCash;
	}

	public double getBuyTransfer() {
		return buyTransfer;
	}

	public void setBuyTransfer(double buyTransfer) {
		this.buyTransfer = buyTransfer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public static void main(String[] args) {
		System.out.println(new DataRecord());
	}

}
