package com.ticket.java.model;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@NotEmpty
	@Column(name = "name")
	private String name;

	@NotNull
	@NotEmpty
	@Column(name = "lastname")
	private String lastname;

	@NotNull
	@NotEmpty
	@Column(name = "username")
	private String username = name + "." + lastname;

	@NotNull
	@NotEmpty
	@Column(name = "password")
	@JsonIgnore
	private String password;

	@NotNull
	@Column(name = "active")
	private boolean active;

	@Formula("(SELECT COUNT(ticket.id) FROM ticket WHERE (ticket.status_id=1 OR ticket.status_id=2) AND ticket.user_id=id)")
	private Integer openTickets;

	@Formula("(SELECT COUNT(ticket.id) FROM ticket WHERE ticket.status_id=3 AND ticket.user_id=id)")
	private Integer closedTickets;

	@NotNull
	@NotEmpty
	@Column(name = "email")
	// TODO inserire constraint per unique
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToMany(mappedBy = "user")
	@JsonBackReference
	private Set<Ticket> tickets;

	@OneToMany(mappedBy = "author")
	@JsonBackReference
	private Set<Note> notes;

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

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
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

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
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
