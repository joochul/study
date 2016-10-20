package sample.carMongo.entity;

import org.springframework.data.annotation.Id;

public class Owner {
    @Id
    private long ownerId;
    
    private String name;
    
    private String area;
    
    private long period;
    
    private Car car;
    
    public Owner() {
    }

    public Owner(long id, String name, String area, long period) {
    	this.ownerId = id;
        this.name   = name;
        this.area   = area;
        this.period = period;
    }
    
    //get, set
	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
    
}
