package com.phillip.news.media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections.map.MultiValueMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.phillip.news.utils.MyFileUtils;

public abstract class AbstractFromListMediaCollector implements MediaCollectionTask{

	private List<String> done;
	protected final Properties properties;
	private List<String> toDo = new ArrayList<String>();
	
	public AbstractFromListMediaCollector(Properties properties){
		this.properties = properties;
		this.done = MyFileUtils.readFileToList(properties.getProperty("historyLocation"));
	}
	
	private void done(String URL){
		done.add(URL);
	}
	
	private boolean wasVisited(String URL){
		return done.contains(URL);
	}
	
	private void pause(Integer ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract List<String> getToDo();
	protected abstract void visit(Document doc);
	
	@Override
	public void run() {
		toDo = getToDo();
		System.out.println("FOUND " + toDo.size() + " entries todo");
		for(String current : toDo){
			if(!wasVisited(current)){
				done(current);
				System.out.println(Thread.currentThread().getName() + " VISITING: " + current);
				try {
					Document document = Jsoup.connect(current).timeout(Integer.parseInt(properties.getProperty("timeout"))).userAgent(properties.getProperty("userAgent")).get();
					visit(document);
				} catch (IOException e) { 
					e.printStackTrace();
				}
				
				pause(Integer.parseInt(properties.getProperty("pause")));
			}
		}
		
		MyFileUtils.writeListToFile(properties.getProperty("historyLocation"), done);
	}
}
