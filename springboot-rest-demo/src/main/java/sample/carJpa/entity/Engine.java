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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // LAZY로 설정된 컬럼일 경우 proxy의 정보도 표현된다. 해당 항목을 제거 하고 표현한다.
@Table(name="ENGINE")
public class Engine {
    @Id
    @Column(name="engine_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long engineId;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = false)
    private String power;
    
    @Column(nullable = false)
    private String gas;
    
    /*
     * optional : 컬럼의 데이터가 없을 경우 무시하고 데이터를 입력하고자 할때는 'true', 무조건 데이터가 있어야 할 경우에는 'false'
     *            nullable = true
     */
    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch=FetchType.EAGER)
    @JoinColumn(name="status_id", nullable = true, unique = true, insertable = true, updatable = false)
    private Status status;
    
    public Engine() {
    }
    public Engine(String type, String power, String gas, Status status) {
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
