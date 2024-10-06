package com.ticket.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {

}
