package sample.noticeboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sample.noticeboard.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post>{
	
	Post findById(int id);
	
	Page<Post> findBySubjectLike(String subject, Pageable pageable);
	
}
