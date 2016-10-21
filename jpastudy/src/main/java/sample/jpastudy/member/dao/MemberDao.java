package sample.jpastudy.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sample.jpastudy.member.model.Member;

public interface MemberDao extends JpaRepository<Member, String>{
	
	/**
	 * 
	 * member_id와 team_id를 변수로 member를 찾을때
	 * 
	 * find - select
	 * By   - where TABLE : MEMBER
	 * Id   - Member 객체의 id 변수 : MEMBER_ID = ?
	 * And  - and 
	 * Team Id - Member의 Team변수(이때 분석으로 TEAM테이블 join)의 id변수 : TEAM_ID = ?
	 * 
	 * @param memberId
	 * @param teamId
	 * @return
	 */
	Member findByIdAndTeamId(String memberId, Long teamId);
	
	
	/**
	 * team_id로 member를 모두 가져올때
	 * 
	 * find - select
	 * By   - where TABLE : MEMBER
	 * Team Id - Member의 Team변수(이때 분석으로 TEAM테이블 join)의 id변수 : TEAM_ID = ?
	 * 
	 * @param teamId
	 * @return
	 */
	List<Member> findByTeamId(Long teamId);
	
	
	/**
	 * NativeQuery를 사용해서 member 정보를 가져오는 경우
	 * 
	 * @param memberName
	 * @return
	 */
	@Query(value= "select a.* "
			+ "from member a "
			+ "where a.member_name like :memberName "
			, nativeQuery=true)
	List<Member> findAllMemberByQuery(@Param("memberName") String memberName );
	
	
}
