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
		Boolean downloadSuccess = false;
		Map<String, String> links = new HashMap<String, String>();
		
		do{
			try{
				originalURL = new URL(imageURL);
				original = ImageIO.read(originalURL);
				downloadSuccess = true;
			}catch(Exception e){
				attempts += 1;
			}
		}while(attempts < 2 && !downloadSuccess);
		
		if(downloadSuccess){
			try{
				BufferedImage imageMedium = ImageUtils.fitToFrame(original, FRAME_MEDIUM, FRAME_MEDIUM);
				String mediumFilename = ImageUtils.createRandomFileName(10) + ".jpg";
				File file;
				if(ImageIO.write(imageMedium, "jpg", file = new File("/home/johndoe/Dokumente/SoftwareDevelopment/STSWorkspace/MediaCollector/data" + File.separator + mediumFilename))){
					//upload to amazon

					AmazonS3Client amazonClient = new AmazonS3Client(new BasicAWSCredentials("AKIAJR42LPOGZ7U7SPFQ", "bj7yQVkrUQA1tpfiXlkrFFH0+bftv79kZrDXXtQO"));
					amazonClient.putObject(new PutObjectRequest("nevermind90", mediumFilename, file));
					
					file.delete();
					
					links.put("small", "http://nevermind90.s3.amazonaws.com/" + mediumFilename);
					links.put("medium", "http://nevermind90.s3.amazonaws.com/" + mediumFilename);
					links.put("large", "http://nevermind90.s3.amazonaws.com/" + mediumFilename);
				}
			} catch(AmazonServiceException ase){
				System.out.println("Caught an AmazonServiceException, which " +
	            		"means your request made it " +
	                    "to Amazon S3, but was rejected with an error response" +
	                    " for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
			} catch(AmazonClientException ace){
				System.out.println("Caught an AmazonClientException, which " +
	            		"means the client encountered " +
	                    "an internal error while trying to " +
	                    "communicate with S3, " +
	                    "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
			} catch(Exception e){}
			/*try{
				BufferedImage imageSmall = ImageUtils.fitToFrame(original, FRAME_SMALL, FRAME_SMALL);
				String smallFilename = ImageUtils.createRandomFileName(10) + ".jpg";
				if(ImageIO.write(imageSmall, "jpg", new File(FilesConfig.TOMCAT_LOCAL_DIRECTORY + File.separator + smallFilename))){
					links.put("small", FilesConfig.DOMAIN_RESOURCES_IMG + File.separator + smallFilename);
				}
			}catch(Exception e){}

			try{
				BufferedImage imageMedium = ImageUtils.fitToFrame(original, FRAME_MEDIUM, FRAME_MEDIUM);
				if(imageMedium.getWidth() > FRAME_SMALL || imageMedium.getHeight() > FRAME_SMALL){
					String mediumFilename = ImageUtils.createRandomFileName(10) + ".jpg";
					if(ImageIO.write(imageMedium, "jpg", new File(FilesConfig.TOMCAT_LOCAL_DIRECTORY + File.separator + mediumFilename))){
						links.put("medium", FilesConfig.DOMAIN_RESOURCES_IMG + File.separator + mediumFilename);
					}
				}
			}catch(Exception e){}
			
			try{
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
		}

		return links;
	}
}
