package com.ticket.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ticket.java.model.Ticket;
import com.ticket.java.service.TicketService;
import com.ticket.java.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class TicketController {

	@Autowired
	TicketService tService;

	@Autowired
	UserService uService;

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

	@GetMapping("/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {

		model.addAttribute("ticket", tService.getById(id));

		return "tickets/show";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("users", uService.findAll());
		return "/tickets/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model) {

		ticket.setStatus("open");
		if (bindingResult.hasErrors()) {
			model.addAttribute("users", uService.findAll());
			return "/tickets/create";
		}

		tService.save(ticket);

		return "redirect:/";
	}
}
