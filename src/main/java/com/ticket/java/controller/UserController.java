package com.ticket.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticket.java.model.User;
import com.ticket.java.service.TicketService;
import com.ticket.java.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService uService;

	@Autowired
	TicketService tService;

	@GetMapping("/{id}")
	public String userPage(Model model, @PathVariable("id") Integer id, Authentication auth) {

		User currentUser = uService.getByUsername(auth.getName());

		if (id != currentUser.getId()) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed here");
		}

		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		// model.addAttribute("tickets", tService.findUserTickets(id));
		return "/users/user";
	}

	@PostMapping("/changestatus/{id}")
	public String changeUserStatus(@PathVariable("id") Integer id, RedirectAttributes feedback, Authentication auth) {

		Integer currentUserId = uService.getByUsername(auth.getName()).getId();

		if (id != currentUserId) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed here");
		}

		User user = uService.getById(id);
		Boolean userStatus = user.isActive();
		Integer openTickets = user.getOpenTickets();

		// user is active (true) and has no open tickets OR user is non active (false)
		// and has open tickets -> status change to
		// non-active(false)
		if ((userStatus && openTickets == 0) || (!userStatus && openTickets > 0)) {
			user.setActive(!userStatus);
			uService.save(user);
			feedback.addFlashAttribute("successMessage", "Status successfully updated");
		}

		// user is active (true) and has open tickets -> cannot change status
		else if (userStatus && openTickets > 0) {
			feedback.addFlashAttribute("dangerMessage", "Cannot change status now, you still have open tickets");
		}

		// user is non active (false) and doesn't have open tickets -> cannot change
		// status
		else if (!userStatus && openTickets == 0) {
			feedback.addFlashAttribute("dangerMessage", "Cannot change status now, you dont have any open ticket");
		}

		return "redirect:/users/{id}";

	}

}
