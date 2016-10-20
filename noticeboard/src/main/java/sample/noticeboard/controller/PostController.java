package sample.noticeboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sample.noticeboard.entity.Post;
import sample.noticeboard.repository.PostRepository;
import sample.noticeboard.specs.CustomerSpecs;

@Controller
public class PostController {
 
	@Autowired
	private PostRepository 	postRepository; 
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showAllPosts(Model model) {
		System.out.println("Index");
		model.addAttribute("posts", postRepository.findAll());
		return "home";
	}
	
	
	@RequestMapping(value = "/savedata")
	public String saveData() {
		System.out.println("saveData");
		
		for (int i = 0; i < 20; i++) {
			Post post = new Post();
			post.setCategory("Category"+i);
			post.setContent("c"+i);
			post.setSubject("s"+i);
			postRepository.save(post);
		}
		return "home";
	}
	
	
	/*
	 * find all
	 */
	@RequestMapping(value = "/main", method=RequestMethod.GET)
	public String getMainPage(Model model) {
		System.out.println("### main ###");
		
		List<Post> post = postRepository.findAll();
		System.out.println("[size]"+post.size());
		model.addAttribute("posts", post);
		
		return "index-corporate-3";
	}
	
	/*
	 * [리스트 출력]
	 * page 기능을 이용한 조회
	 * @PageableDefault 제어 
	 */
	@RequestMapping(value = "/qna", method=RequestMethod.GET)
	public String getQna(Model model, @PageableDefault (size=10, sort={"id"}, direction=Direction.DESC) Pageable pageable) {
		System.out.println("### qna ###");
		
//		List<Post> list = postRepository.findAll(); //전체 조회
		Page<Post> list = postRepository.findAll(pageable); //page 조회
		
		model.addAttribute("lists", list);
		System.out.println("-----------------------------------------");
		System.out.println("[getSize]"+list.getSize());
		System.out.println("[getNumber]"+list.getNumber());
		System.out.println("[getNumberOfElements]"+list.getNumberOfElements());
		System.out.println("[getTotalElements]"+list.getTotalElements());
		System.out.println("[getTotalPages]"+list.getTotalPages());
		System.out.println("[getSort]"+list.getSort());
		System.out.println("-----------------------------------------\n");
		
		return "qna";
	}
	
	/*
	 * [조회]
	 * parameter로 받은 keyword를 사용하여 데이터를 제목을 like 조건으로 조회
	 * 
	 */
	@RequestMapping(value = "/list_form", method=RequestMethod.GET)
	public String getQnaSearch(Model model
			,@PageableDefault (size=10, sort={"id"}, direction=Direction.DESC) Pageable pageable
			,@RequestParam(value="keyword", required=false) String keyword) {
		
		System.out.println("### QnaSearch ### : "+keyword);
		String keyword_like = "%"+keyword+"%";
		
		Page<Post> list = postRepository.findBySubjectLike(keyword_like, pageable);
		model.addAttribute("lists", list);
		
		return "qna";
	}
	/*
	 * [like 조회의 다른 예]
	 * 
	 * 쿼리를 method의 조합으로 구현할 수 있음
	 * 
	 * [Specification 사용법]
	 * Specification을 활용하여 쿼리를 만들어서 조회살 수 있다.
	 */
	@RequestMapping(value = "/specs", method=RequestMethod.GET)
	public String getSpecsPage(Model model
			,@RequestParam(value="keyword", required=false) String keyword
			,@RequestParam(value="id", required=false) Integer id) {
		
		System.out.println("### specs ###");
		
		//specification
		Specification<Post> spec = Specifications.where(CustomerSpecs.likeSearching(keyword))
				                  .or(CustomerSpecs.idCheck(id));
		
		List<Post> post = postRepository.findAll(spec);
		System.out.println("[size]"+post.size());
		model.addAttribute("posts", post);
		
		return "index-corporate-3";
	}
	
	
	/*
	 * [상세정보 확인]
	 * 
	 */
	@RequestMapping(value = "/qna_detail/{id}", method=RequestMethod.GET)
	public String getQnaDetail(Model model
			,@PathVariable int id) {
		
		System.out.println("### qna detail ###"+id);
		
		Post post = (Post) postRepository.findById(id);
		model.addAttribute("post", post);
		
		return "qna_detail";
	}
	
	/*
	 * 쓰기 페이지
	 */
	@RequestMapping(value = "/write", method=RequestMethod.GET)
	public String getWrite(Model model) {
		System.out.println("### write ###");
		
		model.addAttribute("post", new Post());
		
		return "write";
	}
	
	
	/*
	 * 입력
	 */
	@RequestMapping(value = "/posts", method=RequestMethod.POST)
	public String createPost(@ModelAttribute Post post,Model model) {
		System.out.println("### createPost ###");
		
		System.out.println("-----------------------------------------");
		System.out.println("[getSubject]"+post.getSubject());
		System.out.println("[getCategory]"+post.getCategory());
		System.out.println("[getContent]"+post.getContent());
		System.out.println("-----------------------------------------\n");
		
		postRepository.save(post);
		
		return "redirect:qna";
	}
	
	/*
	 * 
	 */
	@RequestMapping(value = "/update/{id}", method=RequestMethod.GET)
	public String getUpdatePost(Model model
			,@PathVariable int id) {
		
		System.out.println("### qna update ###"+id);
		
		Post post = (Post) postRepository.findById(id);
		
		System.out.println("-----------------------------------------");
		System.out.println("[getSubject]"+post.getSubject());
		System.out.println("[getCategory]"+post.getCategory());
		System.out.println("[getContent]"+post.getContent());
		System.out.println("-----------------------------------------\n");
		
		
		model.addAttribute("post", post);
		
		return "update";
	}
	
	/*
	 * 입력
	 */
	@RequestMapping(value = "/actUpdate", method=RequestMethod.POST)
	public String updatePost(@ModelAttribute Post post,Model model) {
		
		System.out.println("### updatePost ###");
		System.out.println("-----------------------------------------");
		System.out.println("[getId]"+post.getId());
		System.out.println("[getSubject]"+post.getSubject());
		System.out.println("[getCategory]"+post.getCategory());
		System.out.println("[getContent]"+post.getContent());
		System.out.println("-----------------------------------------\n");
		
		
		postRepository.save(post);
		
		return "redirect:qna";
	}
	
	
}