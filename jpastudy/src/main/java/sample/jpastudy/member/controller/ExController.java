package sample.jpastudy.member.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.jpastudy.member.dao.MemberDao;
import sample.jpastudy.member.dao.TeamDao;
import sample.jpastudy.member.model.Member;
import sample.jpastudy.member.model.Team;


@RestController
@RequestMapping("/mapping")  // http://localhost:8080/member
public class ExController {

	@Autowired
	private TeamDao teamDao;
	
	@Autowired
	private MemberDao memberDao;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> create(@RequestBody Map<String, Object> paramMap){
		
		String teamName   = paramMap.get("teamname").toString();
		String memberId   = paramMap.get("id").toString();
		String memberName = paramMap.get("name").toString();
		
		Team   team   = new Team(teamName);
		Member member = new Member(memberId, memberName, team);
		
		///////////////////////////////////
		member.setTeam(team);
		teamDao.save(team);
		///////////////////////////////////
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "created successfully");
		response.put("team", team);
		
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Map<String, Object> addMember(@PathVariable("id") Long teamId,
			                 @RequestBody Map<String, Object> memberMap){
		
		//Team team = teamDao.findOne(teamId);
		Team team = new Team(teamId);
		
		String memberId   = memberMap.get("id").toString();
		String memberName = memberMap.get("name").toString();
		
		Member member = new Member(memberId, memberName, team);
		
		Member gMember = memberDao.save(member);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Modify successfully");
		response.put("team", gMember.getTeam());
		response.put("member", gMember);
		
		return response;
	}

}
