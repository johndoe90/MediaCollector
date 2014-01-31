package com.phillip.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.phillip.news.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	Category findById(Long id);
	
	List<Category> findByParentIsNull();
	List<Category> findByIdIn(List<Long> ids);
	Category findByQualifiedName(String qualifiedName);
	
	@Query("SELECT DISTINCT category FROM Category as category LEFT JOIN FETCH category.translations ORDER BY category.sort ASC")
	List<Category> findAllCategories();
}
