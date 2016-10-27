package sample.jpastudy.member.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.jpastudy.member.dao.MemberDao;
import sample.jpastudy.member.dao.MemberDaoImpl;
import sample.jpastudy.member.dao.TeamDao;
import sample.jpastudy.member.model.Member;
import sample.jpastudy.member.model.Team;

/**
 * 
 * http://localhost:8080/mapping
 * 
 * @author Charles.Lee
 *
 */
@RestController
@RequestMapping("/mapping")
public class ExController {

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private MemberDao memberDao;



	/**
	 * 테스트할 샘플 정보 입력
	 * 
	 * http://localhost:8080/mapping/sample
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sample")
	public Map<String, Object> createTeamMember() {

		// test sample
		Team team = null;
		Member member = null;
		List<Member> memberList = new ArrayList();

		team = new Team("team001");

		member = new Member("mem1001", "멤버1001", team);
		memberList.add(member);
		member = new Member("mem1002", "멤버1002", team);
		memberList.add(member);
		member = new Member("mem1003", "멤버1003", team);
		memberList.add(member);

		team.setMemberList(memberList);
		teamDao.save(team);

		memberList.clear();
		team = new Team("team002");

		member = new Member("mem2001", "멤버2001", team);
		memberList.add(member);
		member = new Member("mem2002", "멤버2002", team);
		memberList.add(member);
		member = new Member("mem2003", "멤버2003", team);
		memberList.add(member);

		team.setMemberList(memberList);
		teamDao.save(team);
		
		/////////////////////
		///// 동시 등록 방법  /////
		/////////////////////
		/*
		 * 
		 * ㅁ member를 여러건 등록할때
		 * 
		 * 1. memberList를 생성하고 member객체를 입력함 
		 * 2. team 객체에 세팅함. 완전한 team 객체 생성 
		 * 3. team 저장
		 
		List<Member> memberList = new ArrayList();
		memberList.add(member);
		team.setMemberList(memberList);
		teamDao.save(team);
		 */
		
		/*
		 * ㅁ member를 하나씩 등록할때
		 * 
		 * 1. team을 먼저 등록. id 자동발급을 위해 
		 * 2. member에 Persistent된 team을 세팅 
		 * 3. member 저장
		 * 

		 Team gTeam = teamDao.save(team);
		 member.setTeam(gTeam);
		 memberDao.save(member);
		 */
		// /////////////////////////////////
		
		
		List<Team> teamList = teamDao.findAll();

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "created successfully");
		response.put("sample", teamList);

		return response;
	}

	/**
	 * team 입력
	 * 
	 * http://localhost:8080/mapping/team
	 * {"teamname":"team001"}
	 * 
	 * @param teamMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/team")
	public Map<String, Object> createTeam(@RequestBody Map<String, Object> teamMap) {

		String teamName = teamMap.get("teamname").toString();

		Team team = new Team(teamName);
		Team gTeam = teamDao.save(team);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "created Team successfully");
		response.put("team", gTeam);

		return response;
	}

	/**
	 * team 정보 모두 가져오기
	 * 
	 * http://localhost:8080/mapping/team
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/team")
	public Map<String, Object> getAllTeam() {

		List<Team> teamList = teamDao.findAll();

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "get all team successfully");
		response.put("teamList", teamList);

		return response;
	}

	/**
	 * id에 해당하는 team 정보 가져오기
	 * 
	 * http://localhost:8080/mapping/team/1
	 * 
	 * @param teamId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/team/{id}")
	public Map<String, Object> getTeam(@PathVariable("id") Long teamId) {

		Team team = teamDao.findOne(teamId);
		
		
		//member에 접근... 이때 member를 찾으로 쿼리가 구동된다.
		//System.out.println("[FIRST]"+team.getMemberList().size());
		

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Get Team successfully");
		response.put("team", team);
		//response.put("memberList", team.getMemberList());

		return response;
	}
	
	
	
	// QueryDSL ////////////////////////////////////////////////////////////////
	@Autowired
	private MemberDaoImpl memberDaoImpl;
	/**
	 * QueryDSL 사용 샘플
	 * 
	 * @param teamId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/teamjpql/{id}")
	public Map<String, Object> getTeamJpql(@PathVariable("id") Long teamId) {

		Team team = teamDao.findOne(teamId);
		
		List<Member> result = memberDaoImpl.getTeamMembers(team);

		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Get members successfully");
		response.put("memberList", result);

		return response;
	}
	//////////////////////////////////////////////////////////////////////////
	
	

	/**
	 * id에 해당하는 team 정보 수정하기
	 * 
	 * http://localhost:8080/mapping/team/1
	 * {"teamname":"team001-1"}
	 * 
	 * @param teamId
	 * @param teamMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/team/{id}")
	public Map<String, Object> modyTeam(@PathVariable("id") Long teamId,
			@RequestBody Map<String, Object> teamMap) {

		Team team = teamDao.findOne(teamId);
		String teamName = teamMap.get("teamname").toString();
		team.setName(teamName);

		Team gTeam = teamDao.save(team);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Modify successfully");
		response.put("team", gTeam);

		return response;
	}

	/**
	 * id에 해당하는 team 정보 삭제하기
	 * 
	 * http://localhost:8080/mapping/team/1
	 * 
	 * @param teamId
	 * @param teamMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/team/{id}")
	public Map<String, Object> delTeam(@PathVariable("id") Long teamId) {

		teamDao.delete(teamId);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "delete successfully.");
		response.put("teamid", teamId);

		return response;
	}

	/**
	 * id에 해당하는 team에 member 등록하기
	 * 
	 * http://localhost:8080/mapping/member/1
	 * {"id":"mem1001","name":"멤버1001"}
	 * 
	 * @param teamId
	 * @param memberMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/member/{teamid}")
	public Map<String, Object> addMember(@PathVariable("teamid") Long teamId,
			@RequestBody Map<String, Object> memberMap) {

		Team team = teamDao.findOne(teamId);
		// Team team = new Team(teamId);

		String memberId = memberMap.get("id").toString();
		String memberName = memberMap.get("name").toString();

		Member member = new Member(memberId, memberName, team);

		Member gMember = memberDao.save(member);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "add member successfully");
		response.put("team", gMember.getTeam());
		response.put("member", gMember);

		return response;
	}

	/**
	 * id에 해당하는 team에 속한 모든 member 정보 가져오기
	 * 
	 * http://localhost:8080/mapping/member/1
	 * 
	 * @param teamId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/member/{teamid}")
	public Map<String, Object> getTeamMember(@PathVariable("teamid") Long teamId) {

		// custom method
		List<Member> memberList = memberDao.findByTeamId(teamId);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "get memberList successfully");
		response.put("memberList", memberList);

		return response;
	}

	/**
	 * id에 해당하는 team에 속한 member중 memberId에 대한 정보 가져오기
	 * 
	 * http://localhost:8080/mapping/member/1/mem1001
	 * 
	 * @param teamId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/member/{teamid}/{memberid}")
	public Map<String, Object> getMember(@PathVariable("teamid") Long teamId,
			@PathVariable("memberid") String memberId) {

		// custom method
		Member member = memberDao.findByIdAndTeamId(memberId, teamId);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "get member successfully");
		response.put("member", member);

		return response;
	}
	
	
	
	/**
	 * member name을 통해서 member 정보를 가져오기
	 * 
	 * MemberDao에 Native query를 사용하는 "findAllMemberByQuery" 생성.
	 * like 문 테스트 
	 * 
	 * http://localhost:8080/mapping/membername?name=멤버
	 * 
	 * @param memberName
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/membername")
	public Map<String, Object> getMemberName(@RequestParam("name") String memberName) {

		String likeStr = "%"+memberName+"%";
		
		// custom method
		List<Member> memberList = memberDao.findAllMemberByQuery(likeStr);
		

//		List<MemberTemp> memberTempList = new ArrayList(); 
//		for(Member m : memberList){
//			String id   = m.getId();
//			String name = m.getName();
//			
//			MemberTemp temp = new MemberTemp(id, name);
//			
//			String teamId  = m.getTeam().getId().toString();
//			temp.setTeamId(teamId);
//			
//			memberTempList.add(temp);
//		}
		
		

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "get members successfully");
		response.put("memberList", memberList);
//		response.put("memberList", memberTempList);

		return response;
	}

	/**
	 * id에 해당하는 team에 속한 memberId에 대한 member 정보 수정하기
	 * 
	 * http://localhost:8080/mapping/member/1/mem1001
	 * {"name":"멤버1001-1"}
	 * 
	 * @param teamId
	 * @param memberId
	 * @param memberMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/member/{teamid}/{memberid}")
	public Map<String, Object> modifyMember(
			@PathVariable("teamid") Long teamId,
			@PathVariable("memberid") String memberId,
			@RequestBody Map<String, Object> memberMap) {

		String memberName = memberMap.get("name").toString();

		// custom method
		Member member = memberDao.findByIdAndTeamId(memberId, teamId);

		member.setName(memberName);

		Member gMember = memberDao.save(member);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Modify member successfully");
		response.put("member", gMember);

		return response;
	}

	/**
	 * id에 해당하는 team에 속한 memberId에 대한 member 정보 삭제하기
	 * 
	 * http://localhost:8080/mapping/member/1/mem1001
	 * 
	 * @param teamId
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/member/{teamid}/{memberid}")
	public Map<String, Object> deleteMember(
			@PathVariable("teamid") Long teamId,
			@PathVariable("memberid") String memberId) {

		// custom method
		Member member = memberDao.findByIdAndTeamId(memberId, teamId);
		member.setTeam(null);

		memberDao.delete(member);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "delete member successfully");
		response.put("teamid", teamId);
		response.put("memberId", memberId);

		return response;
	}

}
