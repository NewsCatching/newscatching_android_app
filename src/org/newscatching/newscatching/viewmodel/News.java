package org.newscatching.newscatching.viewmodel;

public class News {

	private String newsID;
	private String imageURL;

	public String getImageURL() {
		return imageURL;
	}
	
	public boolean hasImageURL(){
		return imageURL != null && !"".equals(imageURL.trim());  
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	private boolean isHot;
	private String shareUrl;
	private String source;
	private String title;
	private String content;
	private boolean isSupported;

	public News() {
	}

	public String getNewsID() {
		return newsID;
	}

	public void setNewsID(String newsID) {
		this.newsID = newsID;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isSupported() {
		return isSupported;
	}

	public void setSupported(boolean isSupported) {
		this.isSupported = isSupported;
	}

}
