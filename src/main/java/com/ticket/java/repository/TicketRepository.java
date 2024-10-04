package com.ticket.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

}
