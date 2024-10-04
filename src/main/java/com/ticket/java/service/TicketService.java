package com.ticket.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository tRepo;

}
