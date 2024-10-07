package com.ticket.java.controller;

import java.time.LocalDateTime;
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

import com.ticket.java.model.Note;
import com.ticket.java.model.Ticket;
import com.ticket.java.service.CategoryService;
import com.ticket.java.service.NoteService;
import com.ticket.java.service.TicketService;
import com.ticket.java.service.TicketStatusService;
import com.ticket.java.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class TicketController {

	@Autowired
	TicketService tService;

	@Autowired
	UserService uService;

	@Autowired
	CategoryService cService;

	@Autowired
	TicketStatusService tsService;

	@Autowired
	NoteService nService;

	// READ

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
		model.addAttribute("note", new Note());

		return "tickets/show";
	}

	// CREATE

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("users", uService.findAll());
		model.addAttribute("categories", cService.findAll());
		return "/tickets/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
			model.addAttribute("users", uService.findAll());
			model.addAttribute("categories", cService.findAll());
			return "/tickets/create";
		}
		tService.save(ticket);
		return "redirect:/";
	}

	// EDIT

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("ticket", tService.getById(id));
		model.addAttribute("users", uService.findAll());
		model.addAttribute("categories", cService.findAll());
		model.addAttribute("status", tsService.findAll());

		return "/tickets/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("users", uService.findAll());
			model.addAttribute("categories", cService.findAll());
			model.addAttribute("status", tsService.findAll());

			return "/tickets/edit";
		}

		tService.update(ticket);
		return "redirect:/{id}";
	}

	// NOTE

	@PostMapping("{ticketId}/note")
	public String addNote(@ModelAttribute("note") Note newNote, Model model,
			@PathVariable("ticketId") Integer ticketId) {

		newNote.setTicket(tService.getById(ticketId));
		newNote.getTicket().setUpdatedAt(LocalDateTime.now());
		nService.addNote(newNote);

		return "redirect:/{ticketId}";
	}
	// DELETE

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		tService.destroy(id);
		return "redirect:/";
	}
}
