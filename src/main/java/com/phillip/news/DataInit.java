package com.phillip.news;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.CategoryTranslation;
import com.phillip.news.domain.Language;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.service.CategoryService;
import com.phillip.news.service.LanguageService;
import com.phillip.news.service.MediaProviderService;


@Component
public class DataInit implements InitializingBean{
	
	@Inject
	private Environment env;
	
	@Inject
	private MediaProviderService mediaProviderService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private LanguageService languageService;
	
	private Map<String, Language> languages = new HashMap<String, Language>();
	private Map<String, Category> categories = new HashMap<String, Category>();
	private Map<String, MediaProvider> mediaProviders = new HashMap<String, MediaProvider>();
	
	private Language assembleLanguage(String languageId){
		String languageCode = env.getRequiredProperty("languages." + languageId + ".code");
		
		return new Language(languageCode);
	}
	
	private void initializeLanguages(){
		Language language;
		String[] langs =  env.getRequiredProperty("languages").split(",");
		for(String lang : langs){
			language = assembleLanguage(lang);
			language = languageService.persist(language);
			languages.put(lang, language);
		}
	}
	
	private MediaProvider assembleMediaProvider(String mediaProviderId){
		String mediaProviderName = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".mediaProviderName");
		String logoSmall = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoSmall");
		String logoMedium = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoMedium");
		String logoLarge = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoLarge");
		
		return new MediaProvider(mediaProviderId, mediaProviderName, logoSmall, logoMedium, logoLarge);
	}
	
	private void initializeMediaProviders(){
		MediaProvider mediaProvider;
		String[] provs = env.getRequiredProperty("mediaProviders").split(",");
		for(String prov : provs){
			mediaProvider = assembleMediaProvider(prov);
			mediaProvider = mediaProviderService.persist(mediaProvider);
			mediaProviders.put(prov, mediaProvider);
		}
	}
	
	private List<CategoryTranslation> assembleCategoryTranslations(String categoryId, Category category){
		List<CategoryTranslation> translations = new ArrayList<CategoryTranslation>();
		for(Map.Entry<String, Language> entry : languages.entrySet()){
			translations.add(
				new CategoryTranslation(
					env.getRequiredProperty("categories." + categoryId + ".name." + entry.getKey()),
					category,
					entry.getValue()
				)	
			);
		}
		
		return translations;
	}
	
	private Category assembleCategory(String categoryId){
		String parentName = env.getRequiredProperty("categories." + categoryId + ".parent");
		Category parent = parentName.isEmpty() ? null : categories.get(parentName);
		String qualifiedName = env.getRequiredProperty("categories." + categoryId + ".qualifiedName");

		return new Category(qualifiedName, parent, new ArrayList<CategoryTranslation>());
	}
	
	private void initializeCategories(){
		Category category;
		List<CategoryTranslation> translations;
		String[] cats = env.getRequiredProperty("categories").split(",");
		for(String cat : cats){
			category = assembleCategory(cat);
			translations = assembleCategoryTranslations(cat, category);
			category.setTranslations(translations);
			category = categoryService.persist(category);
			
			categories.put(cat, category);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		/*File temp;
		String[] mediaProviders = env.getRequiredProperty("mediaProviders").split(",");
		String historyLocation = env.getRequiredProperty("mediaProviders.historyLocation");
		for(String mediaProvider : mediaProviders){
			temp = new File()
		}*/
		
		/*File temp = new File(TOMCAT_LOCAL_DIRECTORY);
		
		if(!temp.exists()){ 
			temp.mkdir();
		}*/
		
		if(env.getRequiredProperty("hibernate.hbm2ddl.auto").contains("create")){
			initializeLanguages();
			initializeMediaProviders();
			initializeCategories();
		}
	}
}
