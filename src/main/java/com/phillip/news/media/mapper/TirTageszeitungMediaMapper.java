package com.phillip.news.media.mapper;

import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;

public class TirTageszeitungMediaMapper extends AbstractMediaMapper{

	public TirTageszeitungMediaMapper(MediaProvider mediaProvider){
		super(mediaProvider);
	}
	
	@Override
	public Long getDate(Document document) {
		try {
			Elements elements = document.select("header[id=article_header] > div[id=article_meta] > span.meta_time > time");
			if(!elements.isEmpty()){
				return new SimpleDateFormat("EE, dd.MM.yyyy hh:mm", Locale.GERMANY).parse(elements.first().text()).getTime();
			}
			
			elements = document.select("header[id=article_header] > div[id=article_meta]");
			if(!elements.isEmpty()){
				String text = elements.first().text();
				return new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.GERMANY).parse(text.substring(text.length() - 10) + " 08:00").getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getTitle(Document document) {
		return super.getTitle(document).replace("| Tiroler Tageszeitung Online - Nachrichten von jetzt!", "");
	}

	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String first = parts[1].toLowerCase();
		String second = parts[2].toLowerCase();
		
		switch(first){
			case "politik":
				switch(second){
					case "landespolitik": return Categories.POLITICS_DOMESTIC;
					case "innenpolitik": return Categories.POLITICS_DOMESTIC;
					case "weltpolitik": return Categories.POLITICS_FOREIGN;
					case "europapolitik": return Categories.POLITICS_FOREIGN;
					default: return Categories.POLITICS;
				}
				
			case "wirtschaft": 
				switch(second){
					case "markt": return Categories.ECONOMY_STOCK;
					default: return Categories.ECONOMY;
				}
				
			case "tirol": return Categories.PANORAMA_NATIONAL;
			case "panorama": 
				switch(second){
					case "leute": return Categories.LIFE_PEOPLE;
					case "wissen": return Categories.SCIENCE;
					default: return Categories.PANORAMA_INTERNATIONAL;
				}		
			
			case "sport":
				switch(second){
					case "fussball": return Categories.SPORTS_FOOTBALL;
					case "wintersport": return Categories.SPORTS_WINTERSPORTS;
					default: return Categories.SPORTS;
				}
			
			case "kultur": 
				switch(second){
					case "kinoundtv": return Categories.CULTURE_FILM;
					case "literatur": return Categories.CULTURE_LITERATURE;
					case "buehne": return Categories.CULTURE_STAGE;
					case "musik": return Categories.CULTURE_MUSIC;
					case "kunst": return Categories.CULTURE_ARTS;
					default: return Categories.CULTURE;	
				}
				
			case "lebensart":
				switch(second){
					case "genuss": return Categories.LIFE_FOOD;
					case "gesundheit": return Categories.LIFE_HEALTH;
					case "reise": return Categories.LIFE_TRAVEL;
					case "lifestyle": return Categories.LIFE_LIFESTYLE;
					case "web": return Categories.SCIENCE;
					default: return Categories.LIFE;
				}
			
			case "auto": return Categories.SCIENCE;	
			default: return Categories.OTHER;
		}
	}
}
