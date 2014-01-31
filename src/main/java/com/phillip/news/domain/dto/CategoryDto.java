package com.phillip.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phillip.news.domain.Category;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto {
	private CategoryDto(){}
	public CategoryDto(Category category){
		this.id = category.getId();
		this.qualifiedName = category.getQualifiedName();
		this.sort = category.getSort();
		this.name = category.getTranslations().get(0).getName();
	}
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("qualifiedName")
	private String qualifiedName;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("sort")
	private String sort;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQualifiedName() {
		return qualifiedName;
	}
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
