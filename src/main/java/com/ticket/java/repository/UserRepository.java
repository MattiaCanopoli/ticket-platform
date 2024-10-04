package com.ticket.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
