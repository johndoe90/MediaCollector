package com.phillip.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phillip.news.domain.CategoryTranslation;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryTranslationDto {
	private CategoryTranslationDto(){}
	public CategoryTranslationDto(CategoryTranslation translation){
		this.id = translation.getId();
		this.name = translation.getName();
		this.language = new LanguageDto(translation.getLanguage());
	}
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("language")
	private LanguageDto language;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LanguageDto getLanguage() {
		return language;
	}
	public void setLanguage(LanguageDto language) {
		this.language = language;
	}
}
