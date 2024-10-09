package com.ticket.java.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

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

	public List<User> findAvailable() {
		return uRepo.findByAvailableTrue();
	}

	public String getUserMainRole(Collection<? extends GrantedAuthority> roles) {

		String role = "";
		for (GrantedAuthority gAuth : roles) {
			System.out.println(gAuth);
			if (gAuth.getAuthority().equals("ADMIN")) {
				return role = "ADMIN";

			} else if (gAuth.getAuthority().equals("USER")) {
				return role = "USER";
			}
		}
		return role;
	}

}
