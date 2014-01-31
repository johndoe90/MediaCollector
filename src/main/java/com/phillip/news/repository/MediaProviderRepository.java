package com.phillip.news.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.phillip.news.domain.MediaProvider;


public interface MediaProviderRepository extends JpaRepository<MediaProvider, Long>{
	MediaProvider findByMediaProviderId(String mediaProviderId);
	List<MediaProvider> findByMediaProviderIdLike(String q);
	
	MediaProvider findById(Long id);
	List<MediaProvider> findById(List<Long> ids);
}
