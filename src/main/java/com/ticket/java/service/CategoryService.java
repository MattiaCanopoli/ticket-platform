package com.ticket.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.java.model.Category;
import com.ticket.java.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository cRepo;

	public List<Category> findAll() {
		return cRepo.findAll();
	}
}
