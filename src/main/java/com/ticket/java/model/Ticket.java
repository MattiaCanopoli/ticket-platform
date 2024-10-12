package com.ticket.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "Title can not be empty")
	@NotEmpty(message = "Title can not be empty")
	@Size(max = 50)
	@Column(name = "title", nullable = false)
	private String title;

	@NotNull(message = "Plese describe your request")
	@NotEmpty(message = "Please describe your request")
	@Column(name = "content", columnDefinition = "TEXT", nullable = false)
	private String content;

	@NotNull(message = "Select a priority level")
	@NotEmpty(message = "Select a priority level")
	@Column(nullable = false)
	private String priority;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false, nullable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	// RELATIONS

	@NotNull(message = "Please select an operator")
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonManagedReference
	private User user;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE)
	private List<Note> notes;

	@ManyToMany
	@JoinTable(name = "ticket_categories", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@NotEmpty(message = "Al least one category must be selected")
	private List<Category> categories;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "status_id")
	private TicketStatus status;

	@Transient
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm");

	// getter & setter

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getFormattedCreatedAt() {
		return this.createdAt.format(formatter);
	}

	public String getFormattedUpdatedAt() {
		return this.updatedAt.format(formatter);
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
