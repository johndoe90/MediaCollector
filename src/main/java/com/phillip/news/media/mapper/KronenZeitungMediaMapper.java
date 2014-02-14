package com.phillip.news.media.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;

public class KronenZeitungMediaMapper extends AbstractMediaMapper{
	
	public KronenZeitungMediaMapper(MediaProvider mediaProvider){
		super(mediaProvider);
	}
	
	@Override
	public Long getDate(Document document) {
		try {
			Element element = document.select("div.objekt_vorleger > div.c_time").get(0);
			return new SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.GERMANY).parse(element.text()).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String getType(Document document) {
		return "article";
	}
	
	@Override
	public String getUrl(Document document) {
		return "http://krone.at" + getMetaProperty(document, "meta[name=debug-http-script_url]");
	}

	@Override
	public String getDescription(Document document) {
		return getMetaProperty(document, "meta[name=description]");
	}

	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String first = parts[1].toLowerCase();
		
		switch(first){
			//temp
			case "olympia": return Categories.SPORTS_WINTERSPORTS; 
		
			case "politik": return Categories.POLITICS;
			case "wirtschaft": return Categories.ECONOMY;
			case "wissen": return Categories.SCIENCE;
			case "oesterreich": return Categories.PANORAMA_NATIONAL;
			case "welt": return Categories.PANORAMA_INTERNATIONAL;
			case "nachrichten": return Categories.PANORAMA_INTERNATIONAL;
			
			case "sport": return Categories.SPORTS;
			case "fussball": return Categories.SPORTS_FOOTBALL;
			case "formel-eins": return Categories.SPORTS_MOTORSPORTS;
			
			case "stars-society": return Categories.LIFE_PEOPLE;
			case "lifestyle": return Categories.LIFE_LIFESTYLE;
			case "musik": return Categories.CULTURE_MUSIC;
			
			case "digital": return Categories.SCIENCE;
			case "web": return Categories.SCIENCE;
			case "elektronik": return Categories.SCIENCE;
			case "mobil": return Categories.SCIENCE;
			case "spiele": return Categories.SCIENCE;
			case "apple": return Categories.SCIENCE;
			case "facebook": return Categories.SCIENCE;
			case "google": return Categories.SCIENCE;
			case "microsoft": return Categories.SCIENCE;
			
			case "freizeit": return Categories.LIFE;
			case "gesund-fit": return Categories.LIFE_HEALTH;
			case "familie": return Categories.LIFE;
			case "tierecke": return Categories.LIFE;
			case "reisen-urlaub": return Categories.LIFE_TRAVEL;
			case "bauen-wohnen": return Categories.LIFE_LIVING;
			
			case "auto": return Categories.SCIENCE;
			
			default: return Categories.OTHER;
		}
	}
}
