package sample.carJpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="OWNER")
public class Owner {
    @Id
    @Column(name="owner_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ownerId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = true)
    private String area;
    
    @Column(nullable = false)
    private long period;
    
    
    @ManyToOne
    @JoinColumn(name = "car_id") //car와 연결할 컬럼명. 없다면 변수명으로 생성한다.
    @JsonBackReference   // Json 형태로 표현할때 제외하라는 태그
    private Car car;
    
    
    public Owner() {
    }

    public Owner(String name, String area, long period) {
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
