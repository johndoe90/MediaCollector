package com.phillip.news.media;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;


public abstract class AbstractMediaMapper implements MediaMapper{
	
	protected final MediaProvider mediaProvider;
	
	public AbstractMediaMapper(MediaProvider mediaProvider){
		this.mediaProvider = mediaProvider;
	}
	
	protected String getMetaProperty(Document document, String cssQuery){
		Element element = document.select(cssQuery).first();
		
		return element != null ? element.attr("content") : null;
	}
	
	protected boolean isValidMedia(Document document){
		return (getUrl(document) != null && getType(document) != null && getTitle(document) != null && getDate(document) != null && getCategory(document) != null);
	}
	
	@Override
	public String getUrl(Document document) {
		return getMetaProperty(document, "meta[property=og:url]");
	}

	@Override
	public String getType(Document document) {
		return getMetaProperty(document, "meta[property=og:type]");
	}

	@Override
	public String getTitle(Document document) {
		return getMetaProperty(document, "meta[property=og:title]");
	}

	@Override
	public String getDescription(Document document) {
		return getMetaProperty(document, "meta[property=og:description]");
	}

	@Override
	public String getImage(Document document) {
		return getMetaProperty(document, "meta[property=og:image]");
	}

	@Override
	public String getKeywords(Document document) {
		String keywords = getMetaProperty(document, "meta[name=news_keywords]");
		if(keywords != null)
			return keywords;
		
		keywords = getMetaProperty(document, "meta[name=keywords]");
		if(keywords != null)
			return keywords;
		
		return getTitle(document);
	}

	@Override
	public Long getDate(Document document) {
		return null;
	}
	
	@Override
	public Category getCategory(Document document) {
		return null;
	}

	@Override
	public String getAudio(Document document) {
		return getMetaProperty(document, "meta[property=og:audio]");
	}

	@Override
	public String getVideo(Document document) {
		return getMetaProperty(document, "meta[property=og:video]");
	}

	@Override
	public Media map(Document document) {
		if(isValidMedia(document)){
			String keywords = getTitle(document) + " " + getDescription(document) + " " + getKeywords(document);
			Media media = new Media(mediaProvider, getCategory(document), getDate(document), getTitle(document), getType(document), getUrl(document), keywords);
			media.setDescription(getDescription(document));
			media.setImageSmall(getImage(document));
			media.setAudio(getAudio(document));
			media.setVideo(getVideo(document));
			
			return media;
		}
		
		return null;
	}
}
