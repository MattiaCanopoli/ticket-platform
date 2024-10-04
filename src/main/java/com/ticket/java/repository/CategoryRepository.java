package com.ticket.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ticket.java.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
