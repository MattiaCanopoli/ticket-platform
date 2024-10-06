package com.ticket.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.TicketStatus;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {

}
