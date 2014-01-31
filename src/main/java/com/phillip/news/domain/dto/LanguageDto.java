package com.phillip.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phillip.news.domain.Language;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDto {
	private LanguageDto(){}
	public LanguageDto(Language language){
		this.id = language.getId();
		this.code = language.getCode();
	}
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("code")
	private String code;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
