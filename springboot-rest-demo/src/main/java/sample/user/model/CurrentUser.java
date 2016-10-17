package sample.user.model;

import org.springframework.security.core.authority.AuthorityUtils;

import sample.user.model.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private User user;

	public CurrentUser(User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Role getRole() {
		return user.getRole();
	}
}