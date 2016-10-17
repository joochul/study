package sample.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import sample.jpa.model.BookHistory;
import sample.jpa.repository.BookHistoryRepository;

@RestController
@RequestMapping("/web")
public class WebController {

	@Autowired
	private BookHistoryRepository bookHistoryRepository;
	
	@Autowired
    UserDetailsService userService;
	
	
    /**
     * 로그인 성공
     * @param session
     * @return
     */
    @RequestMapping(value = "/login_web", method = RequestMethod.GET)
    public ModelAndView login_success_web(HttpSession session) {
    	
    	System.out.println("Welcome login_success![session]"+session.getId());
    	
        ModelAndView model = new ModelAndView();
		model.addObject("title",      "Spring Security Hello World");
		model.addObject("message",    "This is protected page!");
		model.addObject("session_id", session.getId());
		
		model.setViewName("main");

		return model;
    }
    
	/**
	 * get List
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	  public ModelAndView getAdmin(){
	    List<BookHistory> bookHs = bookHistoryRepository.findAll();
	    
		ModelAndView model = new ModelAndView();
		model.addObject("title", "This page is ADMIN");
		model.addObject("message", "admin => ADMIN");
		model.addObject("totalBooks", bookHs.size());
		model.addObject("list", bookHs);
		model.setViewName("admin");
		return model;
	  }
	
	/**
	 * get List
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/main")
	  public ModelAndView getMain(HttpSession session){
	    List<BookHistory> bookHs = bookHistoryRepository.findAll();
	    
		ModelAndView model = new ModelAndView();
		model.addObject("title", "This page is MAIN");
		model.addObject("message", "main =>USER, ADMIN");
		model.addObject("session_id", session.getId());
		
		model.setViewName("main");
		
		return model;
	  }
	
}