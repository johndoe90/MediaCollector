package com.phillip.news.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "CATEGORY_TRANSLATIONS")
public class CategoryTranslation extends AbstractPersistable<Long>{
	private CategoryTranslation(){}
	public CategoryTranslation(String name, Category category, Language language){
		this.name = name;
		this.category = category;
		this.language = language;
	}
	
	@Column(name = "NAME")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LANGUAGE_ID", nullable = false)
	private Language language;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
