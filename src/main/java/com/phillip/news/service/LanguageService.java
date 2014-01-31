package com.phillip.news.service;

import com.phillip.news.domain.Language;


public interface LanguageService {
	Language persist(Language language);
	Language findByCode(String code);
	
	Boolean languageIsSupported(String languageCode);
}
