package com.ticket.java.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.Ticket;
import com.ticket.java.repository.TicketRepository;

@Service
public class TicketService {

	@Autowired
	private TicketRepository tRepo;

	public List<Ticket> findAll() {
		List<Ticket> tickets = tRepo.findAll();
		return tickets;
	}

	public List<Ticket> findByTitle(String title) {
		List<Ticket> tickets = tRepo.findByTitleContains(title);
		return tickets;
	}

	public List<Ticket> findByStatus(String status) {
		List<Ticket> tickets = tRepo.findByStatusNameContains(status);
		return tickets;
	}

	public List<Ticket> findByCategory(String category) {
		List<Ticket> tickets = tRepo.findByCategoriesNameContains(category);
		return tickets;
	}

	public List<Ticket> findUserTickets(Integer userId) {
		return tRepo.findByUserId(userId);
	}

	/**
	 * check if ticket with id "id" is present. throws exception if not found
	 * 
	 * @param id
	 * @return ticket with id "id"
	 * @throws Exception
	 */
	public Ticket getById(Integer id) throws Exception {
		Optional<Ticket> ticket = tRepo.findById(id);

		if (ticket.isPresent()) {
			return ticket.get();
		} else {
			throw new Exception("Ticket with id " + id + " has not been found");
		}
	}

	/**
	 * get status name of a given ticket
	 * 
	 * @param ticket
	 * @return String: status name
	 */
	public String getTicketStatusName(Ticket ticket) {
		System.out.println(ticket.getStatus().getName());
		return ticket.getStatus().getName();
	}

	public Ticket save(Ticket ticket) {
		return tRepo.save(ticket);
	}

	public Ticket update(Ticket ticket) {
		return tRepo.save(ticket);
	}

	public void destroy(Integer id) {
		tRepo.deleteById(id);
	}
}
