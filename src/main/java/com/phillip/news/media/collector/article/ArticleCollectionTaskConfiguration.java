package com.phillip.news.media.collector.article;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class ArticleCollectionTaskConfiguration {
	
	private Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|ico|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");
	private Integer maxLevel = 1;
	private String userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";
	private List<String> seeds = new ArrayList<String>();
	private Integer pause = 2500;
	private Integer timeout = 10000;
	private String historyLocation = "";
	
	public ArticleCollectionTaskConfiguration(){}
	public ArticleCollectionTaskConfiguration(String historyLocation, List<String> seeds){
		this.historyLocation = historyLocation;
		this.seeds = seeds;
	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public ArticleCollectionTaskConfiguration setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;

		return this;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public ArticleCollectionTaskConfiguration setUserAgent(String userAgent) {
		this.userAgent = userAgent;

		return this;
	}

	public List<String> getSeeds() {
		return seeds;
	}

	public ArticleCollectionTaskConfiguration setSeeds(List<String> seeds) {
		this.seeds = seeds;

		return this;
	}

	public Pattern getFilters() {
		return filters;
	}

	public void setFilters(Pattern filters) {
		this.filters = filters;
	}

	public Integer getPause() {
		return pause;
	}

	public ArticleCollectionTaskConfiguration setPause(Integer pause) {
		this.pause = pause;

		return this;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public ArticleCollectionTaskConfiguration setTimeout(Integer timeout) {
		this.timeout = timeout;
		
		return this;
	}

	public String getHistoryLocation() {
		return historyLocation;
	}

	public void setHistoryLocation(String historyLocation) {
		this.historyLocation = historyLocation;
	}
	
	
}
