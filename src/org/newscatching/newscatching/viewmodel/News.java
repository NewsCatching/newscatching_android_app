package org.newscatching.newscatching.viewmodel;

public class News {
	private int newsID;
	private String title;
	private String imageUrl;

	public News() {
	}

	public News(int newsID, String title, String imageUrl) {
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

	public int getNewsID() {
		return newsID;
	}

	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

}
