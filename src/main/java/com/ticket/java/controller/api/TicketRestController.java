package com.ticket.java.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<Ticket>> index(@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "status", required = false) String status) {

		List<Ticket> tickets = tService.findAll();

		if (category != null && !category.isEmpty()) {
			tickets = tService.findByCategory(category);
		} else if (status != null && !status.isEmpty()) {
			tickets = tService.findByStatus(status);
		}

		if (tickets.size() == 0) {
			return new ResponseEntity<>(tickets, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

}
