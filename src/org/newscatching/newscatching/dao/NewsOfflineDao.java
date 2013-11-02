package org.newscatching.newscatching.dao;

import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.HotNews;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.util.Log;

public class NewsOfflineDao extends BaseNewsDao {
	private ICacheHolder cacheHolder = null;

	public NewsOfflineDao(ICacheHolder cacheHolder) {
		this.cacheHolder = cacheHolder;
	}

	private <K, T> ReturnMessage<T> doCacheGET(String cache_key, DataConverter<K, T> converter) {
		return doRequest(METHOD_GET, cache_key, converter);
	}

	@SuppressWarnings("unchecked")
	private <K, T> ReturnMessage<T> doRequest(int method, String cache_key, DataConverter<K, T> converter) {
		try {
			String response = cacheHolder.getCache(method, cache_key);

			if (response == null) {
				return new ReturnMessage<T>(false, ReturnMessage.ERROR_NETWORK_NOT_CONNECTED,
						"Not able to get result:", null);
			}

			JSONObject jsonObject = new JSONObject(response);
			T converted = null;
			if (!jsonObject.isNull("data")) {
				if (converter != null) {
					converted = converter.ConvertTo((K) jsonObject.get("data"));
				} else {
					converted = (T) jsonObject.get("data");
				}
			}

			return new ReturnMessage<T>(jsonObject.getBoolean("isSuccess"), ReturnMessage.SUCCESS_IN_CACHE,
					jsonObject.getString("errorMessage"), converted);

		} catch (MalformedURLException e) {
			Log.e("mcall-error", e.getMessage(), e);
		} catch (JSONException e) {
			Log.e("mcall-error", e.getMessage(), e);
		} catch (Exception e) {
			Log.e("mcall-error", e.getMessage(), e);
		}

		return new ReturnMessage<T>(false, ReturnMessage.ERROR_NETWORK_NOT_CONNECTED, "Not able to get result:",
				null);
	}

	private <T> ReturnMessage<T> noCacheReturnMessage() {
		return new ReturnMessage<T>(false, ReturnMessage.ERROR_NETWORK_NOT_CONNECTED, "網路目前尚未連線，所以無法存取資料。", null);
	}

	@Override
	public ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID) {
		return noCacheReturnMessage();
	}

	@Override
	public ReturnMessage<Boolean> ping(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return noCacheReturnMessage();
	}

	@Override
	public ReturnMessage<List<HotNews>> getHotNews() {
		return doCacheGET("getHotNews", null);
	}

	@Override
	public ReturnMessage<News> getNews(String newsID) {
		return doCacheGET("getNews", null);
	}

	@Override
	public ReturnMessage<Object> addNewTalk(String newsID, String talk, String nick) {
		return new ReturnMessage<Object>(false, 0, "", null);
	}

}
