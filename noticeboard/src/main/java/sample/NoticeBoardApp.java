package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import sample.noticeboard.entity.Post;
import sample.noticeboard.repository.PostRepository;



@SpringBootApplication
public class NoticeBoardApp {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(NoticeBoardApp.class, args);
		
		
		/*
		 * 
		 * 테스트를 위해 게시판에 초기 데이터를 넣어주는 부분
		 */
		PostRepository repository = context.getBean(PostRepository.class);
		for(int inx=0; inx<100; inx++){
			repository.save(new Post("제목"+inx, "카테고리"+(inx%3),"본문내용"+inx));
		}
		
		
	}
}