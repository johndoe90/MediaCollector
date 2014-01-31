package com.phillip.news.service;

import java.util.List;

import com.phillip.news.domain.Category;


public interface CategoryService {
	Category persist(Category category);
	
	List<Category> findAll();
	List<Category> findAllCategories();
	
	Category findById(Long id);
	Category findByQualifiedName(String qualifiedName);
	List<Category> findById(List<Long> ids);
}
