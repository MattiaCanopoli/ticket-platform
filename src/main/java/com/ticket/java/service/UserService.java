package com.ticket.java.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.Role;
import com.ticket.java.model.User;
import com.ticket.java.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository uRepo;

	public List<User> findAll() {
		List<User> users = uRepo.findAll();
		return users;
	}

	public User getById(Integer id) {
		return uRepo.findById(id).get();
	}

	public User getByUsername(String username) {
		return uRepo.findByUsername(username).get();
	}

	public List<User> findNonActive() {
		return uRepo.findByActiveFalse();
	}

//	public String getUserMainRole(Collection<? extends GrantedAuthority> roles) {
//
//		String role = "";
//		for (GrantedAuthority gAuth : roles) {
//			if (gAuth.getAuthority().equals("ADMIN")) {
//				return role = "ADMIN";
//
//			} else if (gAuth.getAuthority().equals("USER")) {
//				return role = "USER";
//			}
//		}
//		return role;
//	}

	public List<String> getRolesNameByUsername(String username) {
		User user = getByUsername(username);
		List<String> roles = new ArrayList<String>();

		for (Role role : user.getRoles()) {
			roles.add(role.getRoleName());
		}
		return roles;
	}

	public boolean isAdmin(String username) {

		List<String> roles = getRolesNameByUsername(username);
		if (!roles.contains("ADMIN")) {
			return false;
		}
		return true;
	}

	public User save(User user) {
		return uRepo.save(user);
	}

}
