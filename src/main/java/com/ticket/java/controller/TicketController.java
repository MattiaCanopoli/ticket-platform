package com.ticket.java.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticket.java.model.Note;
import com.ticket.java.model.Ticket;
import com.ticket.java.service.CategoryService;
import com.ticket.java.service.NoteService;
import com.ticket.java.service.TicketService;
import com.ticket.java.service.TicketStatusService;
import com.ticket.java.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
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
	public String index(Model model, @RequestParam(name = "title", required = false) String title,
			Authentication auth) {

		List<Ticket> tickets = new ArrayList<Ticket>();

		if (uService.isAdmin(auth.getName())) {
			tickets = tService.findAll();
		} else {
			Integer userId = uService.getByUsername(auth.getName()).getId();
			tickets = tService.findUserTickets(userId);
		}

		model.addAttribute("tickets", tickets);
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		return "tickets/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer id, Model model, Authentication auth, RedirectAttributes feedback) {

		Ticket ticket = tService.getById(id);
		// List<String> roles = uService.getRolesNameByUsername(auth.getName());

		if (!uService.isAdmin(auth.getName()) && !(auth.getName().equals(ticket.getUser().getUsername()))) {

			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to see this ticket");

		}

		model.addAttribute("ticket", ticket);
		model.addAttribute("note", new Note());
		model.addAttribute("status", tsService.findAll());
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));

		return "tickets/show";
	}

	// CREATE

	@GetMapping("/create")
	public String create(Model model, Authentication auth) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("categories", cService.findAll());
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		model.addAttribute("availableUsers", uService.findNonActive());
		return "/tickets/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback, Authentication auth) {

		if (bindingResult.hasErrors()) {
			// bindingResult.getAllErrors().forEach(error ->
			// System.out.println(error.toString()));
			model.addAttribute("categories", cService.findAll());
			model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
			model.addAttribute("availableUsers", uService.findNonActive());
			return "/tickets/create";
		}
		tService.save(ticket);
		feedback.addFlashAttribute("successMessage", "ticket " + ticket.getId() + " has been created");
		return "redirect:/tickets";
	}

	// EDIT

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, Authentication auth) {
		model.addAttribute("ticket", tService.getById(id));
		model.addAttribute("categories", cService.findAll());
		model.addAttribute("status", tsService.findAll());
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		model.addAttribute("availableUsers", uService.findAll());

		return "/tickets/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback, Authentication auth) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAll());
			model.addAttribute("status", tsService.findAll());
			model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
			model.addAttribute("availableUsers", uService.findAll());

			return "/tickets/edit";
		}

		tService.update(ticket);
		feedback.addFlashAttribute("warningMessage", "Ticket " + ticket.getTitle() + " has been modified");
		return "redirect:/tickets/show/{id}";
	}

	// NOTE

	@PostMapping("/note/{ticketId}")
	public String addNote(@ModelAttribute("note") Note newNote, Model model, @PathVariable("ticketId") Integer ticketId,
			Authentication auth) {

		newNote.setTicket(tService.getById(ticketId));
		newNote.getTicket().setUpdatedAt(LocalDateTime.now());
		newNote.setAuthor(uService.getByUsername(auth.getName()));
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		nService.addNote(newNote);

		return "redirect:/tickets/show/{ticketId}";
	}

	@PostMapping("/statusupdate/{id}")
	public String statusUpdate(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback) {

		if (bindingResult.hasErrors()) {
			feedback.addFlashAttribute("dangerMessage",
					"Status for ticket \"" + ticket.getTitle() + "\" cannot be empty");
			return "redirect:/tickets/show/{id}";
		}

		model.addAttribute("ticket", ticket);
		model.addAttribute("status", tsService.findAll());
		tService.save(ticket);
		feedback.addFlashAttribute("successMessage",
				"Status for ticket \"" + ticket.getTitle() + "\" successfully updated");
		return "redirect:/tickets/show/{id}";
	}

	// DELETE

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes feedback) {
		tService.destroy(id);
		feedback.addFlashAttribute("dangerMessage", "Ticket " + id + " has been deleted");
		return "redirect:/tickets";
	}
}
