package com.phillip.news.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.context.annotation.Bean;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class ImageUtils {

	public static final Integer FRAME_SMALL = 64;
	public static final Integer FRAME_MEDIUM = 175;
	
	public static String createRandomFileName(Integer length){
		String random = "";
		Random rand = new Random();
		String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		for(int i = 0; i < length; i++){
			random += charset.charAt(rand.nextInt(charset.length()));
		}
		
		return random;
	};
	
	public static Integer getImageType(BufferedImage image){
		return image.getType() != 0 ? image.getType() : BufferedImage.TYPE_INT_ARGB;
	}
	
	public static BufferedImage fitToFrame(BufferedImage original, Integer width, Integer height){
		float ratio = ((float) original.getWidth()) / original.getHeight();
		
		if(ratio >= 1){
			return original.getWidth() <= width ? original : resizeImage(original, width, Math.round(height / ratio));
		}	
		else{
			return original.getHeight() <= height ? original : resizeImage(original, Math.round(width * ratio), height);
		}
	}
	
	public static BufferedImage resizeImage(BufferedImage original, Integer width, Integer height){
		BufferedImage resized = new BufferedImage(width, height, getImageType(original));
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();
		
		return resized;
	}
	
	public static Map<String, String> buildImageTree(String imageURL){
		URL originalURL = null;
		BufferedImage original = null;
		Integer attempts = 0;
		Map<String, String> links = new HashMap<String, String>();
		
		do{
			try{
				originalURL = new URL(imageURL);
				original = ImageIO.read(originalURL);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				attempts += 1;
			}
		}while(attempts < 2 && original == null);
		
		if(original != null){
			/*try{
				BufferedImage imageSmall = ImageUtils.fitToFrame(original, FRAME_SMALL, FRAME_SMALL);
				String smallFilename = ImageUtils.createRandomFileName(10) + ".jpg";
				if(ImageIO.write(imageSmall, "jpg", new File(FilesConfig.TOMCAT_LOCAL_DIRECTORY + File.separator + smallFilename))){
					links.put("small", FilesConfig.DOMAIN_RESOURCES_IMG + File.separator + smallFilename);
				}
			}catch(Exception e){}*/

			try{
				BufferedImage imageMedium = ImageUtils.fitToFrame(original, FRAME_MEDIUM, FRAME_MEDIUM);
				if(imageMedium.getWidth() > FRAME_SMALL || imageMedium.getHeight() > FRAME_SMALL){
					String mediumFilename = ImageUtils.createRandomFileName(10) + ".jpg";
					if(ImageIO.write(imageMedium, "jpg", new File(FilesConfig.TOMCAT_LOCAL_DIRECTORY + File.separator + mediumFilename))){
						links.put("medium", FilesConfig.DOMAIN_RESOURCES_IMG + File.separator + mediumFilename);
					}
				}
			}catch(Exception e){}
			
			/*try{
				BufferedImage imageLarge = original;
				if(imageLarge.getWidth() > FRAME_MEDIUM || imageLarge.getHeight() > FRAME_MEDIUM){
					String largeFilename = ImageUtils.createRandomFileName(10) + ".jpg";
					if(ImageIO.write(imageLarge, "jpg", new File(FilesConfig.TOMCAT_LOCAL_DIRECTORY + File.separator + largeFilename))){
						links.put("large", FilesConfig.DOMAIN_RESOURCES_IMG + File.separator + largeFilename);
					}
				}	
			}catch(Exception e){}*/
			
			links.put("width", Integer.toString(original.getWidth()));
			links.put("height", Integer.toString(original.getHeight()));
		}else{
			links.put("small", "http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
			links.put("medium", "http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
			links.put("large", "http://static.pagenstecher.de/uploads/f/f9/f9e/f9e6/photo-not-available.jpg");
			links.put("width", "500");
			links.put("height", "493");
		}

		return links;
	}
}
