package sample.carJpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="STATUS")
public class Status {
    @Id
    @Column(name="status_id", nullable=false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusId;
    
    @Column(nullable = false)
    private long level;
    
    @Column(nullable = false)
    private String message;
    
    
    public Status() {
    }
    public Status(long level, String message) {
        this.level   = level;
        this.message = message;
    }
    
    //get, set
	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
