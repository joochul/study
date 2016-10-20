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

@Entity
@Table(name="APPEARANCE")
public class Appearance {
    @Id
    @Column(name="appr_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long apprId;
    
    @Column(nullable = false)
    private String type;
    
    @Column(nullable = true)
    private String color;
    
    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="status_id", nullable = false, unique = true, insertable = true, updatable = false)
    private Status status;
    
    public Appearance() {
    }
    public Appearance(String type, String color, Status status) {
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
