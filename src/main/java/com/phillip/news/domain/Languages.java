package com.phillip.news.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Languages {
	public static final Language DEFAULT = new Language(Locale.ENGLISH.getLanguage());
	
	public static final Language GERMAN = new Language(Locale.GERMAN.getLanguage());
	public static final Language ENGLISH = new Language(Locale.ENGLISH.getLanguage());
	
	public static List<Language> getSupportedLanguages(){
		return Arrays.asList(GERMAN, ENGLISH);
	}
}
