package com.phillip.news.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;


/**
 * ISO 639-1 codes
 * @author johndoe
 *
 */

@Entity
@Table(name = "LANGUAGES")
public class Language extends AbstractPersistable<Long>{
	private Language(){}
	public Language(String code){
		this.code = code;
	}
	
	@Column(name = "CODE", length = 2, nullable = false)
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
