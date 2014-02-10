package com.phillip.news.media.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		
		switch(category){
			case "sport": return Categories.SPORTS;
			case "kultur": return Categories.CULTURE;
			case "bildung": return Categories.EDUCATION;
			case "panorama": return Categories.PANORAMA;
			case "wirtschaft": return Categories.ECONOMY;
			
			case "web": return Categories.SCIENCE;
			case "wissenschaft": return Categories.SCIENCE;
			
			case "inland": return Categories.POLITICS;
			case "international": return Categories.POLITICS;
			
			case "reisen": return Categories.LIFE;
			case "familie": return Categories.LIFE;
			case "lifestyle": return Categories.LIFE;
			case "gesundheit": return Categories.LIFE;
			
			default: return Categories.OTHER;
		}
	}
}
