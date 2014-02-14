package com.phillip.news.media;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.map.MultiValueMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.utils.MyFileUtils;

public abstract class AbstractCrawlingArticleCollector implements MediaCollectionTask{
	
	private final ArticleCollectionTaskConfiguration config;
	private MultiValueMap todo;
	private MultiValueMap done;
	
	public AbstractCrawlingArticleCollector(ArticleCollectionTaskConfiguration config){
		this.config = config;
		this.todo = new MultiValueMap();
	}
	protected abstract void visit(Document doc);
	protected abstract boolean shouldVisit(String URL);
	
	public ArticleCollectionTaskConfiguration getConfig() {
		return config;
	}

	private void initToDo(List<String> seeds){
		for(String seed : seeds){
			if(!todo.containsValue(0, seed)){
				todo.put(0, seed);
			}
		}
	}
	
	private void removeSeedsFromDone(List<String> seeds){
		for(String seed : seeds){
			done.remove(0, seed);
		}
	}
	
	private String next(Integer level){
		Collection<String> urls = todo.getCollection(level);
		if(urls != null){
			String next = urls.iterator().next().toLowerCase();
			todo.remove(level,  next);
			
			return next;
		}
			
		return null;
	}
	
	private void toDo(Integer level, String URL){
		todo.put(level, URL);
	}
	
	private void addToDone(Integer level, String URL){
		done.put(level, URL);
	}
	
	private boolean wasVisited(String URL){
		return done.containsValue(URL);
	}
	
	private void addNewLinks(Integer level, Document document){
		Elements links = document.select("a[href]");
		for(Element link : links){
			String URL = link.attr("abs:href").toLowerCase();
			toDo(level + 1, URL);
		}
	}
	
	private void pause(Integer ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Document fetch(String URL) throws IOException{
		return Jsoup.connect(URL).timeout(config.getTimeout()).userAgent(config.getUserAgent()).get();
	}
	
	@Override
	public void run() {
		String URL = "";
		Document document;
		Integer level = 0;
		
		done = MyFileUtils.readFileToMultiValueMap(config.getHistoryLocation());
		initToDo(config.getSeeds());
		while(level <= config.getMaxLevel()){
			while((URL = next(level)) != null){
				try {
					if(!wasVisited(URL) && shouldVisit(URL)){		
						addToDone(level, URL);
						document = fetch(URL);
						addNewLinks(level, document);
						visit(document);
						pause(config.getPause());
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
			level += 1;
		}
		
		removeSeedsFromDone(config.getSeeds());
		MyFileUtils.writeMultiValueMapToFile(config.getHistoryLocation(), done);
	}
}
