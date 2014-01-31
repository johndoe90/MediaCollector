package com.phillip.news.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phillip.news.domain.Media;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaDto {

	private MediaDto(){}
	public MediaDto(Media media){
		this.id = media.getId();
		this.mediaProvider = new MediaProviderDto(media.getMediaProvider());
		this.category = new CategoryDto(media.getCategory());
		this.title = media.getTitle();
		this.type = media.getType();
		this.url = media.getUrl();
		this.keywords = media.getKeywords();
		this.audio = media.getAudio();
		this.video = media.getVideo();
		this.description = media.getDescription();
		this.date = media.getDate();
		this.consumed = media.getConsumed();
		
		this.imageSmall = media.getImageSmall();
		this.imageMedium = media.getImageMedium();
		this.imageLarge = media.getImageLarge();
		this.imageWidth = media.getImageWidth();
		this.imageHeight = media.getImageHeight();
	}
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("mediaProvider")
	private MediaProviderDto mediaProvider;
	
	@JsonProperty("category")
	private CategoryDto category;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("keywords")
	private String keywords;
	
	@JsonProperty("audio")
	private String audio;
	
	@JsonProperty("video")
	private String video;
	
	@JsonProperty("imageSmall")
	private String imageSmall;
	
	@JsonProperty("imageMedium")
	private String imageMedium;
	
	@JsonProperty("imageLarge")
	private String imageLarge;
	
	@JsonProperty("imageWidth")
	private Integer imageWidth;
	
	@JsonProperty("imageHeight")
	private Integer imageHeight;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("date")
	private Long date;
	
	@JsonProperty("consumed")
	private Integer consumed;

	public MediaProviderDto getMediaProvider() {
		return mediaProvider;
	}
	public void setMediaProvider(MediaProviderDto mediaProvider) {
		this.mediaProvider = mediaProvider;
	}
	public CategoryDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDto category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getAudio() {
		return audio;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public String getImageSmall() {
		return imageSmall;
	}
	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}
	public String getImageMedium() {
		return imageMedium;
	}
	public void setImageMedium(String imageMedium) {
		this.imageMedium = imageMedium;
	}
	public String getImageLarge() {
		return imageLarge;
	}
	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}
	public Integer getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}
	public Integer getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getConsumed() {
		return consumed;
	}
	public void setConsumed(Integer consumed) {
		this.consumed = consumed;
	}
}
