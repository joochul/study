package sample.exampleCar.entity;

import org.springframework.data.annotation.Id;

public class Tire {
    @Id
    private long tireId;
    
    private String type;
    
    private String brand;
    
    private Status status;
    
    public Tire() {
    }
    public Tire(long id, String type, String brand, Status status) {
    	this.tireId = id;
        this.type   = type;
        this.brand  = brand;
        this.status = status;
    }
    
    //get, set
	public long getTireId() {
		return tireId;
	}
	public void setTireId(long tireId) {
		this.tireId = tireId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
