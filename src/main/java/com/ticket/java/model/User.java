package com.ticket.java.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private String username;

	@NotNull
	@NotEmpty
	@Column(name = "password")
	private String password;

	@NotNull
	@NotEmpty
	@Column(name = "email")
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

}
