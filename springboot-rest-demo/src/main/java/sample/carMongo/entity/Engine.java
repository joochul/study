package sample.carMongo.entity;

import org.springframework.data.annotation.Id;

public class Engine {
    @Id
    private long engineId;
    
    private String type;
    
    private String power;
    
    private String gas;
    
    private Status status;
    
    public Engine() {
    }
    public Engine(long id, String type, String power, String gas, Status status) {
    	this.engineId = id;
        this.type   = type;
        this.power  = power;
        this.gas    = gas;
        this.status = status;
    }
    
    //get, set
	public long getEngineId() {
		return engineId;
	}
	public void setEngineId(long engineId) {
		this.engineId = engineId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
}
