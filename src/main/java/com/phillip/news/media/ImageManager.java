package com.phillip.news.media;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.phillip.news.utils.CommonUtils;


public class ImageManager {
	
	private final Integer IMAGE_WIDTH_SMALL;
	private final Integer IMAGE_HEIGHT_SMALL;
	
	private final Integer IMAGE_WIDTH_MEDIUM;
	private final Integer IMAGE_HEIGHT_MEDIUM;
	
	private final String bucketName;
	private final AmazonS3 amazonS3Client;
	private final String temporaryStorageLocation;
	
	public ImageManager(AmazonS3 amazonS3Client, Properties properties){
		this.amazonS3Client = amazonS3Client;
		this.bucketName = properties.getProperty("bucketName");
		this.temporaryStorageLocation = properties.getProperty("temporaryStorageLocation");
		this.IMAGE_WIDTH_SMALL = Integer.parseInt(properties.getProperty("widthSmall"));
		this.IMAGE_HEIGHT_SMALL = Integer.parseInt(properties.getProperty("heightSmall"));
		this.IMAGE_WIDTH_MEDIUM = Integer.parseInt(properties.getProperty("widthMedium"));
		this.IMAGE_HEIGHT_MEDIUM = Integer.parseInt(properties.getProperty("heightMedium"));
	}
	
	public Integer getImageType(BufferedImage image){
		return image.getType() != 0 ? image.getType() : BufferedImage.TYPE_INT_ARGB;
	}
	
	public BufferedImage resizeImage(BufferedImage original, Integer width, Integer height){
		BufferedImage resized = new BufferedImage(width, height, getImageType(original));
		Graphics2D g = resized.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,  RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(original, 0, 0, width, height, null);
		g.dispose();
		
		return resized;
	}
	
	public BufferedImage fitToFrame(BufferedImage original, Integer width, Integer height){
		float ratio = ((float) original.getWidth()) / original.getHeight();
		
		if(ratio >= 1){
			return original.getWidth() <= width ? original : resizeImage(original, width, Math.round(height / ratio));
		}	
		else{
			return original.getHeight() <= height ? original : resizeImage(original, Math.round(width * ratio), height);
		}
	}
	
	public Map<String, String> processImage(String imageURL){
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
			links.put("width", Integer.toString(original.getWidth()));
			links.put("height", Integer.toString(original.getHeight()));
			
			try{
				File imageFile;
				String filename = CommonUtils.randomString(10) + ".jpg";
				BufferedImage image = this.fitToFrame(original, IMAGE_WIDTH_MEDIUM, IMAGE_HEIGHT_MEDIUM);
				if(ImageIO.write(image, "jpg", imageFile = new File(temporaryStorageLocation + File.separator + filename))){
					try{
						System.out.println("UPLOADING IMAGE: " + imageURL);
						amazonS3Client.putObject(new PutObjectRequest(bucketName, filename, imageFile));
						links.put("small", "http://" + bucketName + ".s3.amazonaws.com/" + filename);
						links.put("medium", "http://" + bucketName + ".s3.amazonaws.com/" + filename);
						links.put("large", "http://" + bucketName + ".s3.amazonaws.com/" + filename);
						links.put("width", Integer.toString(image.getWidth()));
						links.put("height", Integer.toString(image.getHeight()));
					} catch(AmazonServiceException ase){
						System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.");
			            System.out.println("Error Message:    " + ase.getMessage());
			            System.out.println("HTTP Status Code: " + ase.getStatusCode());
			            System.out.println("AWS Error Code:   " + ase.getErrorCode());
			            System.out.println("Error Type:       " + ase.getErrorType());
			            System.out.println("Request ID:       " + ase.getRequestId());
					} catch(AmazonClientException ace){
						System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.");
			            System.out.println("Error Message: " + ace.getMessage());
					} finally{
						imageFile.delete();
					}
				}
			} catch(Exception e){}
		}

		return links;
	}
}
