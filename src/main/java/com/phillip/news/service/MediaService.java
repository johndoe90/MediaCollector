package com.phillip.news.service;

import java.util.List;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;


public interface MediaService {
	Media persist(Media media);
	Media findById(Long id);
	Media findByUrl(String url);
	void consume(Media media);
	Boolean exists(String URL);
	List<Media> findAll();

	List<Media> query(List<Long> categoryIds, List<Long> mediaProviderIds, String q, Integer quantity);
	
	List<Media> findByCategory(Category category);
	List<Media> findByMediaProvider(MediaProvider mediaProvider);
	List<Media> findBetweenThem(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Media last);
	List<Media> findAfterThis(List<Category> categories, List<MediaProvider> mediaProviders, Media last, Integer quantity);
	List<Media> findBeforeThis(List<Category> categories, List<MediaProvider> mediaProviders, Media first, Integer quantity);
	List<Media> findRecent(List<Category> categories, List<MediaProvider> mediaProviders, Integer quantity);
}