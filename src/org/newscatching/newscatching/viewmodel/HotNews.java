package org.newscatching.newscatching.viewmodel;

public class HotNews {
	private String newsID;
	private String title;
	private String imageUrl;

	public HotNews() {
	}

	public HotNews(String newsID, String title, String imageUrl) {
		super();
		this.newsID = newsID;
		this.title = title;
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNewsID() {
		return newsID;
	}

	public void setNewsID(String newsID) {
		this.newsID = newsID;
	}

}
