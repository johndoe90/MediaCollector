package com.phillip.news.media.mapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;

public class OoeNachrichtenMediaMapper extends AbstractMediaMapper{

	public OoeNachrichtenMediaMapper(MediaProvider mediaProvider){
		super(mediaProvider);
	} 
	
	@Override
	public Long getDate(Document document) {
		try {
			Element element = document.select("#artikelsidebar > div.sidebarbox > span.sidebar-datum").first();
			return new SimpleDateFormat("dd. MMMM yyyy - hh:mm", Locale.GERMANY).parse(element.text()).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	

	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String first = parts[1].toLowerCase();
		String second = parts[2].toLowerCase();
		String third = parts[3].toLowerCase();
		
		switch(first){
			case "reisen": return Categories.LIFE_TRAVEL;
			case "oberoesterreich": return Categories.POLITICS_DOMESTIC;
			
			case "nachrichten":
				switch(second){
					case "politik":
						switch(third){
							case "landespolitik": return Categories.POLITICS_DOMESTIC;
							case "innenpolitik": return Categories.POLITICS_DOMESTIC;
							case "aussenpolitik": return Categories.POLITICS_FOREIGN;
							default: return Categories.POLITICS;
						}
					
					case "sport":
						switch(third){
							case "fussball": return Categories.SPORTS_FOOTBALL;
							case "wintersport": return Categories.SPORTS_WINTERSPORTS;
							default: return Categories.SPORTS;
						}
						
					case "kultur":
						switch(third){
							case "musik": return Categories.CULTURE_MUSIC;
							default: return Categories.CULTURE;
						}
					
					case "wirtschaft": return Categories.ECONOMY;
					case "chronik": return Categories.PANORAMA;
					case "weltspiegel": return Categories.PANORAMA_INTERNATIONAL;
					case "society": return Categories.LIFE_PEOPLE;
					case "gesundheit": return Categories.LIFE_HEALTH;
					case "web": return Categories.SCIENCE;
				}
				
			case "freizeit":
				switch(second){
					case "essen_trinken": return Categories.LIFE_FOOD;
				}
				
				
			case "anzeigen":
				switch(second){
					case "karriere": return Categories.EDUCATION;
					case "immobilien": return Categories.REALESTATE;
				}
				
			default: return Categories.OTHER;
		}
	}
}
