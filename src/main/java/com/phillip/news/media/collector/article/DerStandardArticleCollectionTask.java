package com.phillip.news.media.collector.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.phillip.news.domain.Media;
import com.phillip.news.media.AbstractFromListMediaCollector;
import com.phillip.news.media.ImageManager;
import com.phillip.news.media.mapper.DerStandardMediaMapper;
import com.phillip.news.media.mapper.KurierMediaMapper;
import com.phillip.news.service.MediaService;
import com.phillip.news.utils.ImageUtils;

public class DerStandardArticleCollectionTask extends AbstractFromListMediaCollector{

	private final ImageManager imageManager;
	private final MediaService mediaService;
	private final DerStandardMediaMapper mediaMapper;
	
	public DerStandardArticleCollectionTask(Properties properties, DerStandardMediaMapper mediaMapper, MediaService mediaService, ImageManager imageManager) {
		super(properties);
		this.mediaMapper = mediaMapper;
		this.mediaService = mediaService;
		this.imageManager = imageManager;
	}

	@Override
	protected List<String> getToDo() {
		List<String> todo = new ArrayList<String>();
		
		try {
			Document document = Jsoup
								  .connect(properties.getProperty("source"))
								  .timeout(Integer.parseInt(properties.getProperty("timeout")))
								  .userAgent(properties.getProperty("userAgent"))
								  .get();
			
			Elements elements = document.select("url > loc");
			for(Element element : elements){
				todo.add(element.text());
				System.out.println("Adding " + element.text() + " --> todo");
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
		return todo;
	}

	@Override
	protected void visit(Document document) {
		
		Media media = mediaMapper.map(document);
		if(media != null && !mediaService.exists(media.getUrl())){
			if(media.getImageSmall() != null){
				Map<String, String> links = ImageUtils.buildImageTree(media.getImageSmall());
				media.setImageSmall(links.get("small") != null ? links.get("small") : media.getImageSmall());
				media.setImageMedium(links.get("medium"));
				media.setImageLarge(links.get("large"));
				media.setImageWidth(links.get("width") != null ? Integer.parseInt(links.get("width")) : null);
				media.setImageHeight(links.get("height") != null ? Integer.parseInt(links.get("height")) : null);
			}else{
				media.setImageSmall("http://dreamslider.com/portals/0/images/Templates/no-image-available.png");
				media.setImageMedium("http://dreamslider.com/portals/0/images/Templates/no-image-available.png");
				media.setImageLarge("http://dreamslider.com/portals/0/images/Templates/no-image-available.png");
				media.setImageWidth(466);
				media.setImageHeight(224);
			}
			
			mediaService.persist(media);
		}
	}
}
