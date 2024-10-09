package com.ticket.java.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.java.model.Ticket;
import com.ticket.java.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

	@Autowired
	TicketService tService;

	@GetMapping
	public List<Ticket> index(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "status", required = false) String status) {

		List<Ticket> tickets = tService.findAll();

		if (category != null && !category.isEmpty()) {
			tickets = tService.findByCategory(category);
		} else if (status != null && !status.isEmpty()) {
			tickets = tService.findByStatus(status);
		}

		return tickets;
	}

}
