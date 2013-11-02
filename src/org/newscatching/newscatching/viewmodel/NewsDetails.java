package org.newscatching.newscatching.viewmodel;

import java.util.List;

public class NewsDetails {
	private News news;
	private List<Report> reports;
	private List<Talk> talks;

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public List<Talk> getTalks() {
		return talks;
	}

	public void setTalks(List<Talk> talks) {
		this.talks = talks;
	}

}
