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
public class Member{
	
	@Id
	@Column(name = "MEMBER_ID", nullable = false, unique = true)
	private String id;
	
	@Column(name = "MEMBER_NAME", nullable = false)
	private String name;

	/*
	 * <양방향 연결>
     * member <=n==1=> team  
	 * 
	 * 조회 member => json: {member,{team}}
	 *  
	 * joinColumn을 사용하는거는 MEMBER 테이블에 foreign_key를 생성하고 이를 통해서 TEAM 테이블과 join을 하겠다는 것임
	 * 아래에서는 join때 사용할 컬럼 명을 team_id라고 생성함
	 * 
	 * 자동 생성되는 MEMBER 테이블 컬럼 => MEMBER_ID, MEMBER_NAME, TEAM_ID
	 * 
	 * [cascade]를 ALL로 하지 않는 이유는 삭제시 해당 객체도 같이 삭제가 된다.
	 * Team이 삭제가 됐을때 member가 삭제가 되는 것은 자연 스럽지만 member가 삭제됐을때 Team도 삭제가 되기 때문에 
	 * PERSIST 설정으로 놓았다.
	 * 
	 * 
	 * [중요]
	 * FetchType.LAZY //지연로딩
	 *  => Member 객체를 find 할때 연결된 내부 객체는 아직 가져오지 않는다. 즉 team을 select하지 않는다
	 *     Team 객체의 내용을 확인할때 그때서야 Team 테이블을 select 한다. 
	 *               
	 * FetchType.EAGER //즉시로딩
	 *  => Member 객체를 find 할때 연결된 내부 객체까지 모두 가져온다. 
	 *     Team 객체의 내용을 Member테이블과 join해서 find할때 같이 가져온다.
	 * 
	 */
    @ManyToOne(cascade = CascadeType.PERSIST, optional = true, fetch=FetchType.LAZY) //지연로딩
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
