package com.phillip.news.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

/*@NamedNativeQueries({
	@NamedNativeQuery(
		name = "queryMedia", 
		query = "SELECT * FROM MEDIA m "
				  + "WHERE m.CATEGORY_ID IN ( :categories ) "
				    + "AND m.MEDIA_PROVIDER_ID IN ( :mediaProviders ) "
				      + "AND MATCH(m.KEYWORDS) AGAINST( :q ) "
				        + "ORDER BY m.DATE DESC, m.id DESC "
				          + "LIMIT :quantity",
		resultClass = Media.class)
})*/

@Entity
@Table(name = "MEDIA", uniqueConstraints = @UniqueConstraint(columnNames = {"URL"}))
public class Media extends AbstractPersistable<Long>{
	
	private Media(){}	
	public Media(MediaProvider mediaProvider, Category category, Long date, String title, String type, String url, String keywords){
		this.mediaProvider = mediaProvider;
		this.category = category;
		this.date = date;
		this.title = title;
		this.type = type.toLowerCase();
		this.url = url.toLowerCase();	
		this.keywords = keywords;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIA_PROVIDER_ID", nullable = false)
	private MediaProvider mediaProvider;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category category;
	
	@Column(name = "URL", nullable = false, unique = true)
	private String url;
	
	@Column(name = "KEYWORDS", nullable = false, length = 1024)
	private String keywords;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION", nullable = true, length = 512)
	private String description;
	
	@Column(name = "IMAGE_SMALL", nullable = false)
	private String imageSmall;
	
	@Column(name = "IMAGE_MEDIUM", nullable = true)
	private String imageMedium;
	
	@Column(name = "IMAGE_LARGE", nullable = true)
	private String imageLarge;
	
	@Column(name = "IMAGE_WIDTH", nullable = true)
	private Integer imageWidth;
	
	@Column(name = "IMAGE_HEIGHT", nullable = true)
	private Integer imageHeight;
	
	@Column(name = "DATE", nullable = false)
	private Long date;
	
	@Column(name = "AUDIO", nullable = true)
	private String audio;
	
	@Column(name = "VIDEO", nullable = true)
	private String video;
	
	@Column(name = "CONSUMED", nullable = false)
	private Integer consumed = 0;

	public MediaProvider getMediaProvider() {
		return mediaProvider;
	}
	public void setMediaProvider(MediaProvider mediaProvider) {
		this.mediaProvider = mediaProvider;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
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
	public Integer getConsumed() {
		return consumed;
	}
	public void setConsumed(Integer consumed) {
		this.consumed = consumed;
	}
}
