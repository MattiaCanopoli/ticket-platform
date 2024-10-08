package com.ticket.java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsername(String username);

}
