package com.ticket.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.Ticket;
import com.ticket.java.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	TicketRepository tRepo;

	public List<Ticket> findAll() {
		List<Ticket> tickets = tRepo.findAll();
		return tickets;
	}

	public List<Ticket> findByTitle(String title) {
		List<Ticket> tickets = tRepo.findByTitleContains(title);
		return tickets;
	}

	public Ticket getById(Integer id) {
		return tRepo.findById(id).get();
	}

	public Ticket save(Ticket ticket) {
		return tRepo.save(ticket);

	}

}
