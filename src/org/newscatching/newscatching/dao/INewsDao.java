package org.newscatching.newscatching.dao;

import java.util.List;

import org.newscatching.newscatching.viewmodel.HotNews;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

public interface INewsDao {
	public final int METHOD_GET = 0;
	public final int METHOD_POST = 1;
	public final int METHOD_MULTIPART_POST = 2;

	public static final int SHEET_TYPE_ALL = 0;
	public static final int SHEET_TYPE_WORKING = 1;
	public static final int SHEET_TYPE_CLOSE = 1;

	public abstract ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID);

	public abstract ReturnMessage<Boolean> ping(String deviceID, String regID);

	public abstract ReturnMessage<List<HotNews>> getHotNews();

	public abstract ReturnMessage<News> getNews(String newsID);

	public abstract ReturnMessage<Object> addNewTalk(String newsID, String talk, String nick);
	public abstract ReturnMessage<Object> addNewReport(String newsID,String nick, String url,String comment);

}