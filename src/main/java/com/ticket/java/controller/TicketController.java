package com.ticket.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ticket.java.service.TicketService;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/")
public class TicketController {

	@Autowired
	TicketService tService;

	@GetMapping
	public String index(Model model) {

		return "tickets/index";
	}
}
