package sample.jpastudy.member.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TeamTemp {
	
	private Long id;
	
	private String name;

    private List<String> memberIdList;
    
    
    
	public TeamTemp() {
	}
	
	public TeamTemp(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public TeamTemp(Long id, String name, List<String> memberIdList) {
		this.id = id;
		this.name = name;
		this.memberIdList = memberIdList;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMemberIdList() {
		return memberIdList;
	}

	public void setMemberIdList(List<String> memberIdList) {
		this.memberIdList = memberIdList;
	}
	
	

}
