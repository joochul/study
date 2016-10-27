package sample.jpastudy.member.dao;

import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import sample.jpastudy.member.model.Member;
import sample.jpastudy.member.model.QMember;
import sample.jpastudy.member.model.QTeam;
import sample.jpastudy.member.model.Team;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPQLQuery;

/**
 * 
 * MemberDao => MemberDaoImpl
 * 
 * 
 * @author Charles.Lee
 *
 */
public class MemberDaoImpl extends QueryDslRepositorySupport implements IMemberDao {
	
    public MemberDaoImpl() {
		super(Member.class);
	}
    
	@Override
    public List<Member> getTeamMembers(Team team) {
		
		QMember qmember = QMember.member;
        QTeam   qteam   = QTeam.team;

        JPQLQuery query = from(qmember);
        query.leftJoin(qmember.team, qteam).fetch();
        
//
        if (team != null) {
            query.where(qmember.team.id.eq(team.getId()));
        }
        
//       query.orderBy(qmember.name.when("2").then(1).otherwise(0).desc());
        
        SearchResults<Member> result = query.listResults(qmember);
        
        return result.getResults();
	}
	
}
