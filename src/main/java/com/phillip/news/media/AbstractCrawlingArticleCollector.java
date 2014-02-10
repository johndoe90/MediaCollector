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
		//this.done = new MultiValueMap();
		//this.done = MyFileUtils.readFileToMultiValueMap(config.getHistoryLocation());
	}

	private void initToDo(List<String> seeds){
		//synchronized (todo) {
			for(String seed : seeds){
				if(!todo.containsValue(0, seed)){
					todo.put(0, seed);
				}
			}
		//}
	}
	
	private void removeSeedsFromDone(List<String> seeds){
		//synchronized (done) {
			for(String seed : seeds){
				done.remove(0, seed);
			}
		//}
	}
	
	private String next(Integer level){
		//synchronized (todo) {
			Collection<String> urls = todo.getCollection(level);
			if(urls != null){
				String next = urls.iterator().next();
				todo.remove(level,  next);
				
				return next;
			}
			
			return null;
		//}
	}
	
	private void toDo(Integer level, List<String> URLs){
		//synchronized (todo) {
			for(String URL : URLs){
				todo.put(level, URL);
			}
		//}
	}
	
	private void toDo(Integer level, String URL){
		//synchronized (todo) {
			todo.put(level, URL);
		//}
	}
	
	private void done(Integer level, String URL){
		//synchronized (done) {
			done.put(level, URL);
		//}
	}
	
	private boolean isVisited(String URL){
		//synchronized (done) {
			return done.containsValue(URL);
		//}
	}
	
	@Override
	public void run() {
		String URL = "";
		Integer level = 0;
		
		done = MyFileUtils.readFileToMultiValueMap(config.getHistoryLocation());
		initToDo(config.getSeeds());
		while(level <= config.getMaxLevel()){
			while((URL = next(level)) != null){
				if(shouldVisit(URL) && !isVisited(URL)){		
					System.out.println(Thread.currentThread().getName() + " Level: " + level + " / VISITING: " + URL);
					done(level, URL);
					
					try {
						Document document = Jsoup.connect(URL).timeout(config.getTimeout()).userAgent(config.getUserAgent()).get();
						newLinks(level, document);
						visit(document);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					pause(new Random().nextInt(100) + config.getPause());
				}
			}
			level += 1;
		}
		
		removeSeedsFromDone(config.getSeeds());
		MyFileUtils.writeMultiValueMapToFile(config.getHistoryLocation(), done);
	}
	
	private void newLinks(Integer level, Document document){
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
	
	protected void visit(Document doc){
	}
	
	protected abstract boolean shouldVisit(String URL);

	public ArticleCollectionTaskConfiguration getConfig() {
		return config;
	}
}
