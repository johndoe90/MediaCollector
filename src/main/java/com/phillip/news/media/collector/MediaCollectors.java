package com.phillip.news.media.collector;

import java.util.ArrayList;
import java.util.List;

import com.phillip.news.media.MediaCollector;


public class MediaCollectors {

	private List<MediaCollector> mediaCollectors = new ArrayList<MediaCollector>();
	
	public void addMediaCollector(MediaCollector mediaCollector){
		mediaCollectors.add(mediaCollector);
	}
	
	public void setMediaCollectors(List<MediaCollector> mediaCollectors){
		this.mediaCollectors = mediaCollectors;
	}
	
	public List<MediaCollector> getMediaCollectors(){
		return mediaCollectors;
	}
	
	public MediaCollector next(){
		return mediaCollectors.get(0);
	}
}
