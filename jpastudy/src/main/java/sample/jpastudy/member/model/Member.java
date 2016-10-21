package sample.jpastudy.member.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "MEMBER")
public class Member {
	
	@Id
	@Column(name = "MEMBER_ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "MEMBER_NAME", nullable = false)
	private String name;

	/*
	 * 양방향 연결 
     * member <=n==1=> team  
	 * 
	 * 조회 member => json: {member,{team}}
	 *  
	 * joinColumn을 사용하는거는 MEMBER 테이블에 foreign_key를 생성하고 이를 통해서 TEAM 테이블과 join을 하겠다는 것임
	 * 아래에서는 join때 사용할 컬럼 명을 team_id라고 생성함
	 * 
	 * 자동 생성되는 MEMBER 테이블 컬럼 => MEMBER_ID, MEMBER_NAME, TEAM_ID
	 * 
	 */
    @ManyToOne(cascade = CascadeType.ALL, optional = true, fetch=FetchType.LAZY) //지연로딩
    @JoinColumn(name="team_id", nullable = true, insertable = true, updatable = true)
    @JsonIgnore //json으로 표현할때 무한루프를 방지하는 어노테이션
    private Team team;

   

	public Member() {
	}

	public Member(String id, String name, Team team) {
		this.id = id;
		this.name = name;
		this.team = team;
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}
