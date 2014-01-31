package com.phillip.news.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class AmazonS3Config {

	@Inject
	private Environment env;
	
	@Bean
	public AmazonS3 amazonS3(){
		AWSCredentials credentials = new BasicAWSCredentials(env.getRequiredProperty("amazonS3.accessKey"), env.getRequiredProperty("amazonS3.secretKey"));
		return new AmazonS3Client(credentials);
	}
}
