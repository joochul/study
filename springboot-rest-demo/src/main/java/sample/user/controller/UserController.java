package sample.user.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sample.user.model.Role;
import sample.user.model.User;
import sample.user.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	
	/**
	 * get user List
	 * 
	 * ex) http://localhost:8080/user
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getUserList() {
		List<User> userList = userRepository.findAll();
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "success - get userList");
		response.put("userList", userList);
		return response;
	}
	
	/**
	 * get user
	 * 
	 * ex) http://localhost:8080/user/${id}
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public Map<String, Object> getUserInfo(@PathVariable("userId") String userId) {
		User user = userRepository.findOne(userId);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "success - get user");
		response.put("user", user);
		return response;
	}
	
	/**
	 * user 등록
	 * 
	 * ex)
	 * http://localhost:8080/user/{userId}/{userPwd}/{auth}/{enabled}
	 * 
	 * http://localhost:8080/user/admin/admin123/ROLE_ADMIN/true
	 * http://localhost:8080/user/super/super123/ROLE_SUPER/true
	 * http://localhost:8080/user/cust/cust123/ROLE_USER/true
	 * 
	 * @param userId
	 * @param userPwd
	 * @param userName
	 * @param auth
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{userId}/{userPwd}/{auth}/{enabled}")
	public Map<String, Object> createUser(@PathVariable("userId")  String userId  //id
			                            , @PathVariable("userPwd") String userPwd //password
			                            , @PathVariable("auth")    String auth    //권한
			                            , @PathVariable("enabled") String enabled //사용유무
			) {

		//사용 유무
		boolean useFlag = true;
		if(enabled.equals("false")){
			useFlag = false;
		}
		
		//권한
		Role role = null;
		if(auth.equals("ROLE_ADMIN")){
			role = Role.ROLE_ADMIN;
		}else if(auth.equals("ROLE_SUPER")){
			role = Role.ROLE_SUPER;
		}else if(auth.equals("ROLE_USER")){
			role = Role.ROLE_USER;
		}
		
		//페스워드 암호화
		String hashedPassword = passwordEncoder.encode(userPwd);

		//사용자 등록
		User user = new User(userId,hashedPassword,useFlag, role);
		userRepository.save(user);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "userInfo created successfully");
		response.put("user", user);
		return response;
	}
	
		
}