package com.ticket.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotEmpty
	@Column(columnDefinition = "TEXT")
	private String content;

//	@ManyToOne
//	@JoinColumn(name = "author", nullable = false)
//	private User author;

	@ManyToOne
	@JoinColumn(name = "ticket_id", nullable = false)
	private Ticket ticket;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Transient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm");

	// getter & setter

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public User getAuthor() {
//		return author;
//	}
//
//	public void setAuthor(User author) {
//		this.author = author;
//	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getFormattedDate() {
		if (this.createdAt != null) {
			return this.createdAt.format(formatter);
		}

		return null;
	}

}
