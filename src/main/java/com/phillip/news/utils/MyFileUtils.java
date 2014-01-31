package com.phillip.news.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;

public class MyFileUtils {

	public static void writeListToFile(String filepath, List<String> data){
		try {
			File file = new File(filepath);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for(String line : data){
				bufferedWriter.write(line);
				bufferedWriter.newLine();
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeMultiValueMapToFile(String filepath, MultiValueMap data){
		try {
			File file = new File(filepath);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			Object key;
			Collection<String> values;
			Iterator<Object> it = data.keySet().iterator();
			while(it.hasNext()){
				key = it.next();
				values = data.getCollection(key);
				for(String value : values){
					bufferedWriter.write(key.toString() + " " + value);
					bufferedWriter.newLine();
				}
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MultiValueMap readFileToMultiValueMap(String filepath){
		MultiValueMap data = new MultiValueMap();
		
		try{
			File file = new File(filepath);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String temp;
			String[] parts;
			while((temp = bufferedReader.readLine()) != null){
				parts = temp.split(" ");
				data.put(parts[0], parts[1]);
			}
			
			bufferedReader.close();

		} catch (IOException e){
			e.printStackTrace();
		}
		
		return data;
	}
	
	public static List<String> readFileToList(String filepath){
		List<String> data = new ArrayList<String>();
		
		try{
			File file = new File(filepath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String temp;			
			while((temp = bufferedReader.readLine()) != null){
				data.add(temp);
			}
			
			bufferedReader.close();

		} catch (IOException e){
			e.printStackTrace();
		}
		
		return data;
	}
}
