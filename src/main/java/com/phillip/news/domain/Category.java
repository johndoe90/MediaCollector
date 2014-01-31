package com.phillip.news.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name = "CATEGORIES", uniqueConstraints = @UniqueConstraint(columnNames = {"QUALIFIED_NAME"}))
public class Category extends AbstractPersistable<Long>{
	private Category(){}
	public Category(String qualifiedName, Category parent, List<CategoryTranslation> translations){
		this.qualifiedName = qualifiedName;
		this.parent = parent;
		this.translations = translations;
	}
	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable = true)
	private Category parent;
	
	@Column(name = "QUALIFIED_NAME", unique = true)
	private String qualifiedName;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
	private List<CategoryTranslation> translations = new ArrayList<CategoryTranslation>();
	
	@OneToMany(mappedBy = "category")
	private List<Media> media = new ArrayList<Media>();
	
	@Column(name = "SORT")
	private String sort;

	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public String getQualifiedName() {
		return qualifiedName;
	}
	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}
	public List<CategoryTranslation> getTranslations() {
		return translations;
	}
	public void setTranslations(List<CategoryTranslation> translations) {
		this.translations = translations;
	}
	public List<Media> getMedia() {
		return media;
	}
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
