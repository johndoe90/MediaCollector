package com.phillip.news.service;

import java.util.List;

import com.phillip.news.domain.MediaProvider;


public interface MediaProviderService {
	MediaProvider persist(MediaProvider mediaProvider);
	List<MediaProvider> findByMediaProviderIdLike(String q);
	MediaProvider findByMediaProviderId(String mediaProviderId);
	
	MediaProvider findById(Long id);
	List<MediaProvider> findById(List<Long> ids);
	List<MediaProvider> findAll();
}
