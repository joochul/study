package sample.carJpa.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // LAZY로 설정된 컬럼일 경우 proxy의 정보도 표현된다. 해당 항목을 제거 하고 표현한다.
@Table(name="TIRE")
public class Tire {
    @Id
    @Column(name="tire_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tireId;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String brand;
    
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="status_id", nullable = false, unique = true, insertable = true, updatable = false)
    private Status status;
    
    public Tire() {
    }
    public Tire(String type, String brand, Status status) {
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
