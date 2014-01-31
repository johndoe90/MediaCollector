package com.phillip.news.media.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.nodes.Document;

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
		return new Date().getTime();
	}

	@Override
	public Category getCategory(Document document) {
		return Categories.POLITICS_FOREIGN;
	}
}
