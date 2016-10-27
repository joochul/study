package sample.jpastudy.member.dao;

import java.util.List;

import sample.jpastudy.member.model.Member;
import sample.jpastudy.member.model.Team;

public interface IMemberDao{
	
	public List<Member> getTeamMembers(Team team);
	
}
