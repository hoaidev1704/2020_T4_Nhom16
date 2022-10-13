package banks;

public class LogRecord {
	private String id, getDate, getTime, fileName;
	private boolean status;

	public LogRecord(String getDate, String getTime, String fileName, boolean status) {
		super();
		this.getDate = getDate;
		this.getTime = getTime;
		this.fileName = fileName;
		this.status = status;
	}

	public LogRecord() {
		super();
	}

	@Override
	public String toString() {
		return "LogRecord [id=" + id + ", getDate=" + getDate + ", getTime=" + getTime + ", fileName=" + fileName
				+ ", status=" + status + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGetDate() {
		return getDate;
	}

	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
