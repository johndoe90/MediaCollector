package com.phillip.news.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phillip.news.domain.Language;


public interface LanguageRepository extends JpaRepository<Language, Long>{
	Language findByCode(String code);
}
