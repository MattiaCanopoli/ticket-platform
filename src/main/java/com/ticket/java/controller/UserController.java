package com.ticket.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		model.addAttribute("currentUser", uService.getByUsername(auth.getName()));
		// model.addAttribute("tickets", tService.findUserTickets(id));
		return "/users/user";
	}

	@PostMapping("/changestatus/{id}")
	public String changeUserStatus(@PathVariable("id") Integer id, RedirectAttributes feedback) {

		User user = uService.getById(id);
		if (user.getOpenTickets() > 0) {
			feedback.addFlashAttribute("dangerMessage", "Cannot chance status now, you still have open tickets");
			return "redirect:/users/{id}";
		}
		user.setAvailable(true);
		uService.save(user);
		feedback.addFlashAttribute("successMessage", "Status successfully updated");
		return "redirect:/users/{id}";
	}

}
