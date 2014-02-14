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
	}
	
	protected abstract List<String> getToDo();
	protected abstract void visit(Document doc);
	
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
	
	private List<String> getDone(){
		return MyFileUtils.readFileToList(properties.getProperty("historyLocation"));
	}
	
	private Document fetch(String url) throws NumberFormatException, IOException{
		return Jsoup.connect(url).timeout(Integer.parseInt(properties.getProperty("timeout"))).userAgent(properties.getProperty("userAgent")).get();
	}
	
	@Override
	public void run() {
		Document document;
		
		done = getDone();
		toDo = getToDo();
		System.out.println("FOUND " + toDo.size() + " entries todo");
		for(String current : toDo){
			try {
				if(!wasVisited(current)){
					done(current);
					document = fetch(current);
					visit(document);
					pause(Integer.parseInt(properties.getProperty("pause")));
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
		MyFileUtils.writeListToFile(properties.getProperty("historyLocation"), done);
	}
}
