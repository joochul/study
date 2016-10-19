package sample.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sample.user.model.CurrentUser;
import sample.user.model.User;
import sample.user.repository.UserRepository;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {

		
		System.out.println("[SEARCH_USER]"+username);
		
		// 사용자 정보가져오기
		User user = (User) userRepository.findByUsername(username);

		if (user != null) {
			return new CurrentUser(user);
		} else {
			throw new UsernameNotFoundException("could not find the user '"+ username + "'");
		}
	}
}