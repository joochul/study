package sample.jpastudy.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.jpastudy.member.model.Member;

public interface MemberDao extends JpaRepository<Member, String>{

}
