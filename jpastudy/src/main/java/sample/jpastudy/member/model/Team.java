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

@Entity
@Table(name = "TEAM")
public class Team {
	
	@Id
	@Column(name = "TEAM_ID", nullable = false, unique = true)//해당 변수명을 지정한 column명으로 DB에 저장한다.
	@GeneratedValue(strategy = GenerationType.AUTO)//값을 자동 생성한다.
	private Long id;
	
	@Column(name = "TEAM_NAME", nullable = false)
	private String name;

	
    /*
     * 양방향 연결 
     * team <=1==n=> member
     * 
     * 조회 team   => json: {team, memberList:{owner1,owner2,...}}
     * 
     * mappedBy를 이용해서 team에서 발번한 ID를 member가 FK 컬럼으로 관리 하는 것으로 세팅. 
     * memberList의 element인 Member 객체내의 team이라는 변수에 의해 매핑.
     * 즉 매핑될때 사용하는 foreign key는  member에 생성되고 team은 member객체의 team 변수와 관계가 있다는 것임
     * 
     * 자동 생성되는 TEAM 테이블 컬럼 => TEAM_ID, TEAM_NAME
     */
	//@OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch=FetchType.EAGER) //즉시로딩. member에서 설명
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch=FetchType.LAZY) //지연로딩. member에서 설명
    //@JsonIgnore //json으로 표현할때 무한루프를 방지하는 어노테이션
    private List<Member> memberList;
	
	
	public Team() {
	}

	public Team(String name) {
		this.name = name;
	}
	
	public Team(String name, List<Member> memberList) {
		this.name = name;
		this.memberList = memberList;
	}
	
	public Team(Long id) {
		this.id = id;
	}
	
	public Team(Long id, String name) {
		this.id = id;
		this.name = name;
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

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

}
