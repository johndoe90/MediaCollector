package com.phillip.news.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "MEDIA_PROVIDERS", uniqueConstraints = @UniqueConstraint(columnNames = "MEDIA_PROVIDER_ID"))
public class MediaProvider extends AbstractPersistable<Long>{

	private MediaProvider(){}
	public MediaProvider(String mediaProviderId, String mediaProviderName, String logoSmall, String logoMedium, String logoLarge){
		this.mediaProviderId = mediaProviderId;
		this.mediaProviderName = mediaProviderName;
		this.logoSmall = logoSmall;
		this.logoMedium = logoMedium;
		this.logoLarge = logoLarge;
	}
	
	@OneToMany(mappedBy = "mediaProvider")
	private List<Media> media = new ArrayList<Media>();
	
	@Column(name = "MEDIA_PROVIDER_ID", nullable = false, unique = true)
	private String mediaProviderId;
	
	@Column(name = "MEDIA_PROVIDER_NAME", nullable = false)
	private String mediaProviderName;
	
	@Column(name = "LOGO_SMALL", nullable = false)
	private String logoSmall;
	
	@Column(name = "LOGO_MEDIUM", nullable = false)
	private String logoMedium;
	
	@Column(name = "LOGO_LARGE", nullable = false)
	private String logoLarge;

	public List<Media> getMedia() {
		return media;
	}
	public void setMedia(List<Media> media) {
		this.media = media;
	}
	public String getMediaProviderId() {
		return mediaProviderId;
	}
	public void setMediaProviderId(String mediaProviderId) {
		this.mediaProviderId = mediaProviderId;
	}
	public String getMediaProviderName() {
		return mediaProviderName;
	}
	public void setMediaProviderName(String mediaProviderName) {
		this.mediaProviderName = mediaProviderName;
	}
	public String getLogoSmall() {
		return logoSmall;
	}
	public void setLogoSmall(String logoSmall) {
		this.logoSmall = logoSmall;
	}
	public String getLogoMedium() {
		return logoMedium;
	}
	public void setLogoMedium(String logoMedium) {
		this.logoMedium = logoMedium;
	}
	public String getLogoLarge() {
		return logoLarge;
	}
	public void setLogoLarge(String logoLarge) {
		this.logoLarge = logoLarge;
	}
}
