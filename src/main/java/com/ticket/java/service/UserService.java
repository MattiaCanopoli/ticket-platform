package com.ticket.java.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	public List<User> findAvailable() {
		return uRepo.findByAvailableTrue();
	}

	public List<String> getUserRoles(String username) {

		List<String> list = new ArrayList<String>();
		Set<Role> roles = getByUsername(username).getRoles();

		for (Role role : roles) {
			list.add(role.getRoleName());
		}

		return list;
	}

	public String getUserMainRole(String username) {
		List<String> roles = getUserRoles(username);

		String role;

		if (roles.contains("ADMIN")) {
			role = "ADMIN";
		} else {
			role = "USER";
		}
		return role;
	}

}
