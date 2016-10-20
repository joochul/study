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
import sample.jpastudy.member.model.Member;


@RestController
@RequestMapping("/member")  // http://localhost:8080/member
public class MemberController {
	
	@Autowired
	private MemberDao memberDao;
	
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public Map<String, Object> createMember(@RequestBody Map<String, Object> memberMap){
		
		String memberId   = memberMap.get("id").toString();
		String memberName = memberMap.get("name").toString();
		Member member = new Member(memberId, memberName);
		
		memberDao.save(member);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "created successfully");
		response.put("member", member);
		
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public Map<String, Object> updateMember(@PathVariable("id") String memberId,
			                 @RequestBody Map<String, Object> memberMap){
				
		Member oriMember = memberDao.findOne(memberId);
		System.out.println("[oriMember]"+oriMember.toString());
		
		
		String memberName = memberMap.get("name").toString();
		Member member = new Member(memberId, memberName);
		
		memberDao.save(member);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Modify successfully");
		response.put("member", member);
		
		return response;
	}

}
