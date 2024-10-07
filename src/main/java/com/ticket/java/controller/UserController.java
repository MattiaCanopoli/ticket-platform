package com.ticket.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ticket.java.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService uService;

	@GetMapping("/{id}")
	public String userPage(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("user", uService.getById(id));
		return "/users/user";
	}

}
