package com.ticket.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
