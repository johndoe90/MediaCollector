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
		String category = parts[2].toLowerCase();
		
		switch(category){			
			case "leben": return Categories.LIFE;
			case "sport": return Categories.SPORTS;
			case "kultur": return Categories.CULTURE;
			case "politik": return Categories.POLITICS;
			case "bildung": return Categories.EDUCATION;
			case "panorama": return Categories.PANORAMA;
			
			case "meingeld": return Categories.ECONOMY;
			case "wirtschaft": return Categories.ECONOMY;
			
			case "science": return Categories.SCIENCE;
			case "techscience": return Categories.SCIENCE;
		
			default: return Categories.OTHER;
		}
	}
}
