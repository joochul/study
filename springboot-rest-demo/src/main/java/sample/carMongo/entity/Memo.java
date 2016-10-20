package sample.carMongo.entity;

import org.springframework.data.annotation.Id;

public class Memo {
    @Id
    private long memoId;
    
    private String message;
    
    private Car car;
    
    
    public Memo() {
    }
    public Memo(long id, String message) {
    	this.memoId = id;
        this.message = message;
    }
    
    //get, set
	public long getMemoId() {
		return memoId;
	}
	public void setMemoId(long memoId) {
		this.memoId = memoId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
}
