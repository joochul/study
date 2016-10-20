package sample.noticeboard.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import sample.noticeboard.entity.Post;

public class CustomerSpecs {
	public static Specification<Post> idCheck(int id){
		
		return new Specification<Post>() {
			
			@Override
			public Predicate toPredicate(Root<Post> root
					                    , CriteriaQuery<?> arg1
					                    , CriteriaBuilder cb) {
				return cb.equal(root.get("id"),id);
			}
			
		};	
	}
	
public static Specification<Post> likeSearching(String txt){
		
		return new Specification<Post>() {
			
			@Override
			public Predicate toPredicate(Root<Post> root
                    , CriteriaQuery<?> arg1
                    , CriteriaBuilder cb) {
				return cb.like(root.get("subject"), "%"+txt+"%");
			}
			
		};
	}
}
