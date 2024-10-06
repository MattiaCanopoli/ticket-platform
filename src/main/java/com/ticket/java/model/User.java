package com.ticket.java.model;

import java.util.Set;

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
	@Column(name = "password")
	private String password;

	@NotNull
	@NotEmpty
	@Column(name = "email")
	// TODO inserire constraint per unique
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	@OneToMany(mappedBy = "user")
	private Set<Ticket> tickets;

//	@OneToMany(mappedBy = "author")
//	private Set<Note> notes;

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

//	public Set<Note> getNotes() {
//		return notes;
//	}
//
//	public void setNotes(Set<Note> notes) {
//		this.notes = notes;
//	}

}
