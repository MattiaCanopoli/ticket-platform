package com.ticket.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	public List<Ticket> findByTitleContains(String title);

	public List<Ticket> findByUserId(Integer userId);

}
