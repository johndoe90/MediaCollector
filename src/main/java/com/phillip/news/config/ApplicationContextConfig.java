package com.phillip.news.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.amazonaws.services.s3.AmazonS3;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.domain.MediaProviders;
import com.phillip.news.media.ImageManager;
import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.MediaCollectorImpl;
import com.phillip.news.media.collector.MediaCollectors;
import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.media.collector.article.DiePresseArticleCollectionTask;
import com.phillip.news.media.collector.article.KurierArticleCollectionTask;
import com.phillip.news.service.MediaService;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = "com.phillip.news")
@PropertySource("classpath:application.properties")
public class ApplicationContextConfig extends WebMvcConfigurerAdapter{
	
	@Inject private Environment env;
	@Inject private AmazonS3 amazonS3Client;
	@Inject private MediaService mediaService;
	@Inject private MediaCollector kurierMediaCollector;
	
	@Bean
	public ImageManager imageManager(){
		return new ImageManager(
			amazonS3Client,
			env.getRequiredProperty("amazonS3.images.bucketName"), 
			env.getRequiredProperty("amazonS3.images.temporaryLocalStorageLocation"));
	}
	
	@Bean
	public MediaCollectors mediaCollectors(){
		MediaCollectors mediaCollectors = new MediaCollectors();
		mediaCollectors.addMediaCollector(kurierMediaCollector);
		
		return mediaCollectors;
		
	}
	
	/*public MediaCollector kurierMediaCollector(){
		MediaCollector kurierMediaCollector = new MediaCollectorImpl();
		ArticleCollectionTaskConfiguration config = assembleConfiguration(MediaProviders.KURIER);
		KurierArticleCollectionTask kurierArticleCollectionTask = 
			new KurierArticleCollectionTask(config, MediaProviders.KURIER, mediaService);
		kurierMediaCollector.addMediaCollectionTask(kurierArticleCollectionTask);
		
		return kurierMediaCollector;
	}
	
	public MediaCollector diePresseMediaCollector(){
		MediaCollector diePresseMediaCollector = new MediaCollectorImpl();
		ArticleCollectionTaskConfiguration config = assembleConfiguration(MediaProviders.DIE_PRESSE);
		DiePresseArticleCollectionTask diePresseArticleCollectionTask = 
			new DiePresseArticleCollectionTask(config, MediaProviders.DIE_PRESSE, mediaService);
		diePresseMediaCollector.addMediaCollectionTask(diePresseArticleCollectionTask);
		
		return diePresseMediaCollector;
	}*/
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public InternalResourceViewResolver configureInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}

