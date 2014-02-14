package com.phillip.news.media.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;
import com.phillip.news.media.ImageManager;
import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.service.MediaService;

public class DerStandardMediaMapper extends AbstractMediaMapper{
	public DerStandardMediaMapper(MediaProvider mediaProvider) {
		super(mediaProvider);
	}
	
	@Override
	public Long getDate(Document document) {
		try {
			String date = document.select("span.date").get(0).text();
			return new SimpleDateFormat("d. MMMM yyyy, hh:mm", Locale.GERMANY).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Category getCategory(Document document) {
		Elements elements = document.select("div[id=breadcrumb] > span.item[typeof=v:Breadcrumb] > a[property=v:title]");
		String category = elements.get(0).text().toLowerCase();
		String subCategory = elements.get(1).text().toLowerCase();
		
		switch(category){
			case "sport": 
				switch(subCategory){
					case "wintersport": return Categories.SPORTS_WINTERSPORTS;
					case "fußball": return Categories.SPORTS_FOOTBALL;
					case "motorsport": return Categories.SPORTS_MOTORSPORTS;
					default: return Categories.SPORTS;
				}
				
			case "kultur": 
				switch(subCategory){
					case "film": return Categories.CULTURE_FILM;
					case "bühne": return Categories.CULTURE_STAGE;
					case "bildende kunst": return Categories.CULTURE_ARTS;
					case "musik": return Categories.CULTURE_MUSIC;
					case "literatur": return Categories.CULTURE_LITERATURE;
					default: return Categories.CULTURE;
				}

			case "panorama": 
				switch(subCategory){
					case "österreich-chronik": return Categories.PANORAMA_NATIONAL;
					case "wien": return Categories.PANORAMA_NATIONAL;
					case "welt-chronik": return Categories.PANORAMA_INTERNATIONAL;
					default: return Categories.PANORAMA;
				}
			
			case "wirtschaft": 
				switch(subCategory){
					case "finanzen & börse": return Categories.ECONOMY_STOCK;
					case "geldstandard": return Categories.ECONOMY_STOCK;
					default: return Categories.ECONOMY;
				}
			
			case "familie": return Categories.LIFE;
			case "reisen": return Categories.LIFE_TRAVEL;
			case "gesundheit": return Categories.LIFE_HEALTH;
			case "lifestyle": 
				switch(subCategory){
					case "essen & trinken": return Categories.LIFE_FOOD;
					case "design & interieur": return Categories.LIFE_LIVING;
					case "mode & kosmetik": return Categories.LIFE_LIFESTYLE;
					default: return Categories.LIFE_LIFESTYLE;
				}

			case "bildung": return Categories.EDUCATION;
			
			case "web": return Categories.SCIENCE;
			case "wissenschaft": return Categories.SCIENCE;
			
			case "inland": return Categories.POLITICS_DOMESTIC;
			case "international": return Categories.POLITICS_FOREIGN;
			
			default: return Categories.OTHER;
		}
	}
}
