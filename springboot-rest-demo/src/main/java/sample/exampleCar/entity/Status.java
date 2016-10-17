package sample.exampleCar.entity;

import org.springframework.data.annotation.Id;

public class Status {
    @Id
    private long statusId;
    
    private long level;
    
    private String message;
    
    public Status() {
    }
    public Status(long id, long level, String message) {
    	this.statusId = id;
        this.level   = level;
        this.message = message;
    }
    
    //get, set
	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
