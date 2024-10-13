package com.ticket.java.model;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotEmpty
	@Size(min = 2)
	@Column(name = "name")
	private String name;

	@NotNull
	@NotEmpty
	@Size(min = 2)
	@Column(name = "lastname")
	private String lastname;

	@NotNull
	@NotEmpty
	@Column(name = "username")
	private String username = name + "." + lastname;

	@NotNull
	@NotEmpty
	@Size(min = 8, max = 24)
	@JsonIgnore
	@Column(name = "password")
	private String password;

	@NotNull
	@Column(name = "is_active", columnDefinition = "BOOLEAN")
	private boolean active;

	/**
	 * Counts all the ticket assigned to a User, with status 1 (OPEN) or 2 (IN
	 * PROGRESS)
	 */
	@Formula("(SELECT COUNT(ticket.id) FROM ticket WHERE (ticket.status_id=1 OR ticket.status_id=2) AND ticket.user_id=id)")
	private Integer openTickets;

	/**
	 * Counts all the ticket assigned to a User, with status 3 (COMPLETED)
	 */
	@Formula("(SELECT COUNT(ticket.id) FROM ticket WHERE ticket.status_id=3 AND ticket.user_id=id)")
	private Integer closedTickets;

	@NotNull
	@NotEmpty
	@Email
	@Column(name = "email", unique = true)
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private List<Ticket> tickets;

	@OneToMany(mappedBy = "author")
	@JsonBackReference
	private List<Note> notes;

	// getter & setter

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Integer getOpenTickets() {
		return openTickets;
	}

	public void setOpenTickets(Integer openTickets) {
		this.openTickets = openTickets;
	}

	public Integer getClosedTickets() {
		return closedTickets;
	}

	public void setClosedTickets(Integer closedTickets) {
		this.closedTickets = closedTickets;
	}

}
