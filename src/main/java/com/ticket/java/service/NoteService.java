package com.ticket.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.Note;
import com.ticket.java.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	NoteRepository nRepo;

	public List<Note> findAll() {
		return nRepo.findAll();
	}

	public Note getById(Integer id) {
		return nRepo.findById(id).get();
	}

	public Note addNote(Note note) {
		return nRepo.save(note);
	}
}
