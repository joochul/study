package sample.carMongo.entity;

import org.springframework.data.annotation.Id;

public class Appearance {
    @Id
    private long apprId;
    
    private String type;
    
    private String color;
    
    private Status status;
    
    public Appearance() {
    }
    public Appearance(long id, String type, String color, Status status) {
    	this.apprId = id;
        this.type   = type;
        this.color  = color;
        this.status = status;
    }
    
    //get, set
	public long getApprId() {
		return apprId;
	}
	public void setApprId(long apprId) {
		this.apprId = apprId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
    
    
}
