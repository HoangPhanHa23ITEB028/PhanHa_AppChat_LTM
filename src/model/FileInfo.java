package model;

import lombok.Data;

@Data
public class FileInfo {

	private String fileName;
	private long fileSize;
	private String urlUpload;
	
	public FileInfo(String fileName, long fileSize, String urlUpload) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.urlUpload = urlUpload;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUrlUpload() {
		return urlUpload;
	}

	public void setUrlUpload(String urlUpload) {
		this.urlUpload = urlUpload;
	}

	
	
}