package model;

import org.bson.Document;

import lombok.Data;

@Data
public class Attachment {
    private String id;
    private String messageId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private int size;

    public Document toDocument() {
        return new Document("message_id", messageId)
            .append("file_name", fileName)
            .append("file_url", fileUrl)
            .append("file_type", fileType)
            .append("size", size);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
    
}