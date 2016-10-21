package sample.jpastudy.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.jpastudy.member.model.Team;

public interface TeamDao extends JpaRepository<Team, Long>{

}
