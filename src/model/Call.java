package model;

import java.time.LocalDateTime;

import org.bson.Document;

import lombok.Data;

@Data
public class Call {
    private String id;
    private String callerId;
    private String receiverId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // "missed", "accepted", "declined"

    public Document toDocument() {
        return new Document("caller_id", callerId)
            .append("receiver_id", receiverId)
            .append("start_time", startTime.toString())
            .append("end_time", endTime.toString())
            .append("status", status);
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}