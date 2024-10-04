package com.ticket.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket.java.model.Ticket;
import com.ticket.java.service.CategoryService;
import com.ticket.java.service.TicketService;

@Controller
@RequestMapping("/")
public class TicketController {

	@Autowired
	TicketService tService;

	@Autowired
	CategoryService cService;

	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title) {

		List<Ticket> tickets;

		if (title != null && !title.isEmpty()) {
			tickets = tService.findByTitle(title);
		} else {
			tickets = tService.findAll();
		}

		model.addAttribute("tickets", tickets);

		return "tickets/index";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("categories", cService.findAll());
		return "/tickets/create";
	}
}
