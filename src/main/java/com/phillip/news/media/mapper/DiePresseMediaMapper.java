package com.phillip.news.media.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;

public class DiePresseMediaMapper extends AbstractMediaMapper{
	
	public DiePresseMediaMapper(MediaProvider mediaProvider) {
		super(mediaProvider);
	}

	@Override
	public Long getDate(Document document) {
		try {
			Elements elements = document.select("time[itemprop=datePublished]");
			if(elements.isEmpty()){
				elements = document.select("p[class=articletime]");
			}

			return new SimpleDateFormat("dd.MM.yyyy | hh:mm", Locale.GERMANY).parse(elements.get(0).text()).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String top = parts[2].toLowerCase();
		String sub = parts[3].toLowerCase();
		
		switch(top){			
			case "leben":
				switch(sub){
					case "mensch": return Categories.LIFE_PEOPLE;
					case "mode": return Categories.LIFE_LIFESTYLE;
					case "gesundheit": return Categories.LIFE_HEALTH;
					case "ausgehen": return Categories.LIFE_FOOD;
					case "reise": return Categories.LIFE_TRAVEL;
					case "wohnen": return Categories.LIFE_LIVING;
					default: return Categories.LIFE;
				}
			
			case "sport":
				switch(sub){
					case "olympia": return Categories.SPORTS_WINTERSPORTS; 
					case "wintersport": return Categories.SPORTS_WINTERSPORTS;
					case "motorsport": return Categories.SPORTS_MOTORSPORTS;
					case "fussball": return Categories.SPORTS_FOOTBALL;
					default: return Categories.SPORTS;
				}
			
			case "kultur":
				switch(sub){
					case "news": return Categories.CULTURE_STAGE;
					case "kunst": return Categories.CULTURE_ARTS;
					case "film": return Categories.CULTURE_FILM;
					case "klassik": return Categories.CULTURE_MUSIC;
					case "popco": return Categories.CULTURE_MUSIC;
					case "literatur": return Categories.CULTURE_LITERATURE;
					default: return Categories.CULTURE;
				}
			
			case "politik":
				switch(sub){
					case "innenpolitik": return Categories.POLITICS_DOMESTIC;
					case "aussenpolitik": return Categories.POLITICS_FOREIGN;
					default: return Categories.POLITICS;
				}
			
			case "panorama": 
				switch(sub){
					case "wien": return Categories.PANORAMA_NATIONAL;
					case "oesterreich": return Categories.PANORAMA_NATIONAL;
					case "welt": return Categories.PANORAMA_INTERNATIONAL;
					default: return Categories.PANORAMA;
				}
					
			case "wirtschaft": return Categories.ECONOMY;	
			case "meingeld":
				switch(sub){
					case "aktien": return Categories.ECONOMY_STOCK;
					case "anleihen": return Categories.ECONOMY_STOCK;
					case "fonds": return Categories.ECONOMY_STOCK;
					case "immobilien": return Categories.ECONOMY_STOCK;
					case "sparprodukte": return Categories.ECONOMY_STOCK;
					case "verbraucher": return Categories.ECONOMY_STOCK;
					case "versicherungen": return Categories.ECONOMY_STOCK;
					default: return Categories.ECONOMY;
				}
		
			case "science": return Categories.SCIENCE;
			case "bildung": return Categories.EDUCATION;
			case "techscience": return Categories.SCIENCE;
			default: return Categories.OTHER;
		}
	}
}
