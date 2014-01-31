package com.phillip.news.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.phillip.news.domain.MediaProvider;
import com.phillip.news.repository.MediaProviderRepository;
import com.phillip.news.service.MediaProviderService;

@Service
public class MediaProviderServiceImpl implements MediaProviderService{

	private final MediaProviderRepository mediaProviderRepository;
	
	@Inject
	public MediaProviderServiceImpl(MediaProviderRepository mediaProviderRepository){
		this.mediaProviderRepository = mediaProviderRepository;
	}

	@Override
	public MediaProvider persist(MediaProvider mediaProvider) {
		Assert.notNull(mediaProvider);
		
		return mediaProviderRepository.save(mediaProvider);
	}
	
	@Override
	public List<MediaProvider> findByMediaProviderIdLike(String q) {
		Assert.hasText(q);
		
		return mediaProviderRepository.findByMediaProviderIdLike(q.trim().toLowerCase() + "%");
	}

	@Override
	public MediaProvider findByMediaProviderId(String mediaProviderId) {
		Assert.hasText(mediaProviderId);
		
		return mediaProviderRepository.findByMediaProviderId(mediaProviderId);
	}

	@Override
	public MediaProvider findById(Long id) {
		Assert.notNull(id);
		
		return mediaProviderRepository.findById(id);
	}

	@Override
	public List<MediaProvider> findById(List<Long> ids) {
		Assert.notEmpty(ids);
		
		return mediaProviderRepository.findById(ids);
	}

	@Override
	public List<MediaProvider> findAll() {
		return mediaProviderRepository.findAll();
	}
}
