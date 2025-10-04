package model;

import java.time.LocalDateTime;

import org.bson.Document;

import lombok.Data;

@Data
public class ChatMessage {

	private String id;
	private String senderId;
	private String receiverId; // hoặc roomId nếu chat nhóm
	private String content;
	private String type; // "text", "image", "file", "sticker", "call"
	private LocalDateTime timestamp;
	private boolean isRead;

	public Document toDocument() {
		return new Document("sender_id", senderId).append("receiver_id", receiverId).append("content", content)
				.append("type", type).append("timestamp", timestamp.toString()).append("is_read", isRead);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}