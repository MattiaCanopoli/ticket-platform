package com.ticket.java.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ticket.java.model.User;
import com.ticket.java.repository.UserRepository;

@Service
public class DbTicketUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository uRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = uRepo.findByUsername(username);

		if (user.isPresent()) {
			return new DbTickekUserDetails(user.get());
		} else
			throw new UsernameNotFoundException(username + " is not found");

	}

}
