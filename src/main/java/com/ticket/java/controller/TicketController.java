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
	private TicketService tService;

	@Autowired
	private UserService uService;

	@Autowired
	private CategoryService cService;

	@Autowired
	private TicketStatusService tsService;

	@Autowired
	private NoteService nService;

	// READ

	/**
	 * shows ticket list, different by authority: ADMIN: all tickes/filtered by
	 * title if requested; USER: only user ticket
	 * 
	 * @param model
	 * @param title
	 * @param auth
	 * @return String, path to index.html
	 */
	@GetMapping
	public String index(Model model, @RequestParam(name = "title", required = false) String title,
			Authentication auth) {

		List<Ticket> tickets = new ArrayList<Ticket>();

		if (uService.isAdmin(auth.getName())) {

			if (title != null && !title.isEmpty()) {
				tickets = tService.findByTitle(title);
			} else {
				tickets = tService.findAll();
			}
		} else {
			Integer userId = uService.getByUsername(auth.getName()).getId();
			tickets = tService.findUserTickets(userId);
		}

		model.addAttribute("tickets", tickets);
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		return "tickets/index";
	}

	/**
	 * shows ticket with id "id"
	 * 
	 * @param id
	 * @param model
	 * @param auth
	 * @param feedback
	 * @return String. path to show.html
	 * @throws Exception NOT_FOUND: ticket with requested id is not present;
	 *                   FORBIDDEN a user not owner of the ticket try to access
	 */
	@GetMapping("/show/{id}")
	public String show(@PathVariable("id") Integer id, Model model, Authentication auth, RedirectAttributes feedback)
			throws Exception {

		Ticket ticket = null;
		try {
			ticket = tService.getById(id);
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

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

	/**
	 * Shows ticket creation form. availableUsers list all users with "USER" role
	 * and active=false
	 * 
	 * @param model
	 * @param auth
	 * @return path to create.html
	 */
	@GetMapping("/create")
	public String create(Model model, Authentication auth) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("categories", cService.findAll());
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		model.addAttribute("availableUsers", uService.findNonActiveUsers());
		return "/tickets/create";
	}

	/**
	 * validate fields and save ticket into database and redirect to index and
	 * display success message. if validation fails, return create form with error
	 * messages.
	 * 
	 * @param ticket
	 * @param bindingResult
	 * @param model
	 * @param feedback
	 * @param auth
	 * @return
	 */
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback, Authentication auth) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAll());
			model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
			model.addAttribute("availableUsers", uService.findNonActiveUsers());
			return "/tickets/create";
		}
		tService.save(ticket);
		feedback.addFlashAttribute("successMessage", "ticket " + ticket.getId() + " has been created");
		return "redirect:/tickets";
	}

	// EDIT

	/**
	 * shows ticket edit form, with prepopulated fields. availableUser list all
	 * users with "USER" authority
	 * 
	 * @param id
	 * @param model
	 * @param auth
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model, Authentication auth) {

		Ticket ticket = null;
		try {
			ticket = tService.getById(id);
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		model.addAttribute("ticket", ticket);
		model.addAttribute("categories", cService.findAll());
		model.addAttribute("status", tsService.findAll());
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		model.addAttribute("availableUsers", uService.findOnlyUsers());

		return "/tickets/edit";
	}

	/**
	 * validate fields and update ticket, then redirect to show page ad display a
	 * message. if validation fails, return edit page with errors
	 * 
	 * @param ticket
	 * @param bindingResult
	 * @param model
	 * @param feedback
	 * @param auth
	 * @return
	 */
	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback, Authentication auth) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", cService.findAll());
			model.addAttribute("status", tsService.findAll());
			model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
			model.addAttribute("availableUsers", uService.findOnlyUsers());

			return "/tickets/edit";
		}

		tService.update(ticket);
		feedback.addFlashAttribute("warningMessage", "Ticket " + ticket.getTitle() + " has been modified");
		return "redirect:/tickets/show/{id}";
	}

	// NOTE

	/**
	 * checks if ticket exist or throw ResponseStatusException with 404 status. add
	 * note in relation to ticket. ticket updatedAt is set
	 * 
	 * @param newNote
	 * @param model
	 * @param ticketId
	 * @param auth
	 * @return
	 */
	@PostMapping("/note/{ticketId}")
	public String addNote(@ModelAttribute("note") Note newNote, Model model, @PathVariable("ticketId") Integer ticketId,
			Authentication auth) {

		Ticket ticket = null;
		try {
			ticket = tService.getById(ticketId);
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

		newNote.setTicket(ticket);
		newNote.getTicket().setUpdatedAt(LocalDateTime.now());
		newNote.setAuthor(uService.getByUsername(auth.getName()));
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		nService.addNote(newNote);

		return "redirect:/tickets/show/{ticketId}";
	}

	/**
	 * validate entry and updates ticket status. if validation fails, redirect to
	 * show with error message dangerMessage
	 * 
	 * @param ticket
	 * @param bindingResult
	 * @param model
	 * @param feedback
	 * @return
	 */
	@PostMapping("/statusupdate/{id}")
	public String statusUpdate(@Valid @ModelAttribute("ticket") Ticket ticket, BindingResult bindingResult, Model model,
			RedirectAttributes feedback) {

		if (bindingResult.hasErrors()) {
			feedback.addFlashAttribute("dangerMessage",
					"Status for ticket \"" + ticket.getTitle() + "\" can't be empty");
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

	/**
	 * delete ticket by id. redirect to index and display confirmation message
	 * 
	 * @param id
	 * @param feedback
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes feedback) {
		tService.destroy(id);
		feedback.addFlashAttribute("dangerMessage", "Ticket " + id + " has been deleted");
		return "redirect:/tickets";
	}
}
