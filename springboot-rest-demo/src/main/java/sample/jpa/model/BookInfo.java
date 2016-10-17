package sample.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOOKINFO")
public class BookInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="info_id", unique = true, nullable = false)
	private long id;
	
	private String place = "";
	
	private String keepDate = "";

	
	
	public BookInfo() {
	}

	public BookInfo(String place, String keepDate) {
		this.place = place;
		this.keepDate = keepDate;
	}
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getKeepDate() {
		return keepDate;
	}

	public void setKeepDate(String keepDate) {
		this.keepDate = keepDate;
	}
	
	
	
}
