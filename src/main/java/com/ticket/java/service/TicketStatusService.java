package com.ticket.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.TicketStatus;
import com.ticket.java.repository.TicketStatusRepository;

@Service
public class TicketStatusService {

	@Autowired
	private TicketStatusRepository tsRepo;

	public List<TicketStatus> findAll() {
		return tsRepo.findAll();
	}

	public TicketStatus getById(Integer id) {
		return tsRepo.findById(id).get();
	}

}
