package com.phillip.news.utils;

import java.util.Random;

public class CommonUtils {

	public static String randomString(Integer length){
		return randomString(length, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}
	
	public static String randomString(Integer length, String charset){
		String random = "";
		Random rand = new Random();
		for(int i = 0; i < length; i++){
			random += charset.charAt(rand.nextInt(charset.length()));
		}
		
		return random;
	}
}
