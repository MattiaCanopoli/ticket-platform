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
		Boolean userStatus = user.isActive();
		Integer openTickets = user.getOpenTickets();

		// user is active (true) and has no open tickets -> status change to
		// non-active(false)
		if ((userStatus && openTickets == 0) || (!userStatus && openTickets > 0)) {
			user.setActive(!userStatus);
			uService.save(user);
			feedback.addFlashAttribute("successMessage", "Status successfully updated");
		}

		// user is active (true) and has open tickets -> cannot change status
		if (userStatus && openTickets > 0) {
			feedback.addFlashAttribute("dangerMessage", "Cannot change status now, you still have open tickets");
		}

		// user is non active (false) and doesn't have open tickets -> cannot change
		// status
		if (!userStatus && openTickets == 0) {
			feedback.addFlashAttribute("dangerMessage", "Cannot change status now, you dont have any open ticket");
		}
		// user is non active (false) and doesn't have open tickets -> cannot change
//		if (!userStatus && openTickets > 0) {
//			user.setActive(true);
//			uService.save(user);
//			feedback.addFlashAttribute("successMessage", "Status successfully updated");
//		}

//
//		if (user.isActive()) {
//			user.setActive(false);
//			feedback.addFlashAttribute("successMessage", "Status successfully updated");
//		}
//
//		if (user.getOpenTickets() == 0 && !user.isActive()) {
//			user.setActive(true);
//			feedback.addFlashAttribute("successMessage", "Status successfully updated");
//		}
//
//		if (user.getOpenTickets() > 0 && !user.isActive()) {
//			feedback.addFlashAttribute("dangerMessage", "Cannot chance status now, you still have open tickets");
//			return "redirect:/users/{id}";
//		}
//
//		feedback.addFlashAttribute("successMessage", "Status successfully updated");
//		user.setActive(true);
//		uService.save(user);
		return "redirect:/users/{id}";

	}

}
