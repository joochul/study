package sample.exampleCar.entity;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Car {
	
	/* 
	## 객체 구조 ##
	sample data 
	: {"carId":1,"name":"소나타나","brand":"현기","year":"2016","memo":{"memoId":1,"message":"Relatively good condition is required for the tire replacement"},"ownerList":[{"ownerId":1,"name":"kim","area":"seoul","period":3},{"ownerId":2,"name":"lee","area":"pusan","period":2}],"appearance":{"apprId":1,"type":"sedan","color":"red","status":{"statusId":1,"level":1,"message":"good"}},"engine":{"engineId":1,"type":"two wheel","power":"100","gas":"gasoline","status":null},"tire":{"tireId":1,"type":"wide","brand":"michelin","status":{"statusId":2,"level":3,"message":"Needs replacement"}}}
	
	[car]
		name, 
		brand, 
		year,
		
		[memo] 
			message
			
	    [owner(list)]
	    	name, 
	    	area, 
	    	period
	    	
	    [appearance] 
	    	type, 
	    	color,
	    	
	    	[status] 
	    		level, 
	    		message
	    		
	    [engine] 
	    	type, 
	    	power, 
	    	gas,
	    	
			[status] 
				level, 
				message
				
	    [tire] 
	    	type, 
	    	brand,
	    	
			[status] 
				level, 
				message
	*/
	
    @Id
    private long carId;
    
    private String name;
    
    private String brand;
    
    private String year;
    
    private Memo memo;
    
    private List<Owner> ownerList;
    
    private Appearance appearance;
    
    private Engine engine;
    
    private Tire tire;
    
    
    /*
     * constructor
     */
    public Car() {
    }
    
    public Car(long id, String name, String brand, String year, List<Owner> ownerList, Memo memo, Appearance appearance, Engine engine, Tire tire) {
    	this.carId      = id;
        this.name       = name;
        this.brand      = brand;
        this.year       = year;
        this.ownerList  = ownerList;
        this.memo       = memo;
        this.appearance = appearance;
        this.engine     = engine;
        this.tire       = tire;
    }

    
    //set get
	public long getCarId() {
		return carId;
	}

	public void setCarId(long carId) {
		this.carId = carId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Owner> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(List<Owner> ownerList) {
		this.ownerList = ownerList;
	}

	public Memo getMemo() {
		return memo;
	}

	public void setMemo(Memo memo) {
		this.memo = memo;
	}

	public Appearance getAppearance() {
		return appearance;
	}

	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Tire getTire() {
		return tire;
	}

	public void setTire(Tire tire) {
		this.tire = tire;
	}
	
}
