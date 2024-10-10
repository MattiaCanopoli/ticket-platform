package com.ticket.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String changeUserStatus() {
		System.out.println("test");
		return "redirect:/tickets";
	}

}
