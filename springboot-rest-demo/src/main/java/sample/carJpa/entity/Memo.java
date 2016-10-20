package sample.carJpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="MEMO")
public class Memo {
    @Id
    @Column(name="memo_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memoId;
    
    @Column(nullable = false)
    private String message;
    
    @OneToOne
    @JoinColumn(name = "car_id") //car와 연결할 컬럼명. 없다면 변수명으로 생성한다.
    @JsonBackReference   // Json 형태로 표현할때 제외하라는 태그
    private Car car;
    
    
    public Memo() {
    }
    public Memo(String message) {
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
