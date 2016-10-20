package sample.jpastudy.member.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id
	@Column(name = "MEMBER_ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "MEMBER_NAME", nullable = false)
	private String name;

	
	
	public Member() {
	}

	public Member(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
