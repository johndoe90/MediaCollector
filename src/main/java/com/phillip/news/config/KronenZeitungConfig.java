package com.phillip.news.config;

import java.io.File;
import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.services.s3.AmazonS3;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.domain.MediaProviders;
import com.phillip.news.media.ImageManager;
import com.phillip.news.media.MediaCollectionTask;
import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.MediaCollectorImpl;
import com.phillip.news.media.MediaMapper;
import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.media.collector.article.KronenZeitungArticleCollectionTask;
import com.phillip.news.media.mapper.KronenZeitungMediaMapper;
import com.phillip.news.service.MediaService;

@Configuration
public class KronenZeitungConfig {
	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private ImageManager imageManager;
	@Inject private MediaService mediaService;

	private static final MediaProvider mediaProvider = MediaProviders.KRONEN_ZEITUNG;
	
	@Bean(name = "kronenZeitungMediaCollector")
	public MediaCollector kronenZeitungMediaCollector(){
		MediaCollector mediaCollector = new MediaCollectorImpl();
		mediaCollector.addMediaCollectionTask(articleCollectionTask());
		 
		return mediaCollector;
	}
	
	public ArticleCollectionTaskConfiguration articleCollectionTaskConfiguration(){
		return new ArticleCollectionTaskConfiguration(
			env.getRequiredProperty("mediaProviders.historyLocation") + File.separator + mediaProvider.getMediaProviderId(),
			Arrays.asList(env.getRequiredProperty("mediaProviders." + mediaProvider.getMediaProviderId() + ".seeds").split(" "))
		);
	}
 
	public MediaCollectionTask articleCollectionTask(){
		return new KronenZeitungArticleCollectionTask(
			articleCollectionTaskConfiguration(), 
			mediaMapper(), 
			mediaService,
			imageManager);
	}
	
	public MediaMapper mediaMapper(){
		return new KronenZeitungMediaMapper(mediaProvider);
	}
}
