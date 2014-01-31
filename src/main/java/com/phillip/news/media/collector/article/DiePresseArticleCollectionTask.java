package com.phillip.news.media.collector.article;

import java.util.Arrays;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractArticleCollector;
import com.phillip.news.media.mapper.DiePresseMediaMapper;
import com.phillip.news.service.MediaService;
import com.phillip.news.utils.ImageUtils;

public class DiePresseArticleCollectionTask extends AbstractArticleCollector{
	private final MediaService mediaService;
	private final DiePresseMediaMapper mediaMapper;
	
	public DiePresseArticleCollectionTask(ArticleCollectionTaskConfiguration config, MediaProvider mediaProvider, MediaService mediaService) {
		super(config);
		this.mediaService = mediaService;
		this.mediaMapper = new DiePresseMediaMapper(mediaProvider);
	}

	@Override
	protected boolean shouldVisit(String URL) {
		if(getConfig().getFilters().matcher(URL).matches())
			return false;

		for(String seed : getConfig().getSeeds()){
			if(URL.startsWith(seed) && !URL.endsWith("/print.do") && !URL.contains("#"))
				return true;
		}
		
		return false;
	}

	//potentiell in abstract article collector geben
	@Override
	protected void visit(Document document) {
		Media media = mediaMapper.map(document);
		if(media != null && !mediaService.exists(media.getUrl())){
			Map<String, String> links = ImageUtils.buildImageTree(media.getImageSmall());
			media.setImageSmall(links.get("small") != null ? links.get("small") : media.getImageSmall());
			media.setImageMedium(links.get("medium"));
			media.setImageLarge(links.get("large"));
			media.setImageWidth(links.get("width") != null ? Integer.parseInt(links.get("width")) : null);
			media.setImageHeight(links.get("height") != null ? Integer.parseInt(links.get("height")) : null);
			
			mediaService.persist(media);
		}
	}
}
