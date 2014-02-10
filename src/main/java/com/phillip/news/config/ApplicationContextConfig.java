package com.phillip.news.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

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
	@Inject private MediaCollector diePresseMediaCollector;
	@Inject private MediaCollector derStandardMediaCollector;
	
	@Bean
	public ImageManager imageManager(){
		Properties properties = new Properties();
		properties.setProperty("bucketName", env.getRequiredProperty("amazonS3.images.bucketName"));
		properties.setProperty("defaultImageSmall", env.getRequiredProperty("amazonS3.images.defaultImageSmall"));
		properties.setProperty("defaultImageMedium", env.getRequiredProperty("amazonS3.images.defaultImageMedium"));
		properties.setProperty("defaultImageLarge", env.getRequiredProperty("amazonS3.images.defaultImageLarge"));
		properties.setProperty("defaultImageWidth", env.getRequiredProperty("amazonS3.images.defaultImage.width"));
		properties.setProperty("defaultImageHeight", env.getRequiredProperty("amazonS3.images.defaultImage.height"));
		properties.setProperty("temporaryStorageLocation", env.getRequiredProperty("amazonS3.images.temporaryLocalStorageLocation"));
		properties.setProperty("widthSmall", env.getRequiredProperty("amazonS3.images.widthSmall"));
		properties.setProperty("heightSmall", env.getRequiredProperty("amazonS3.images.heightSmall"));
		properties.setProperty("widthMedium", env.getRequiredProperty("amazonS3.images.widthMedium"));
		properties.setProperty("heightMedium", env.getRequiredProperty("amazonS3.images.heightMedium"));
		
		return new ImageManager(amazonS3Client, properties);
	}
	
	@Bean
	public MediaCollectors mediaCollectors(){
		MediaCollectors mediaCollectors = new MediaCollectors();
		mediaCollectors.addMediaCollector(kurierMediaCollector);
		mediaCollectors.addMediaCollector(derStandardMediaCollector);
		mediaCollectors.addMediaCollector(diePresseMediaCollector);
		
		return mediaCollectors;
		
	}
	
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

