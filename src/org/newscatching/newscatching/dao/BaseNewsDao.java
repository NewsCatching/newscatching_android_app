package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.NewsPreference;
import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.util.ConnectionDetector;
import org.newscatching.newscatching.util.LogUtil;

import android.content.Context;
import android.content.SharedPreferences;

abstract public class BaseNewsDao implements INewsDao {
	@SuppressWarnings("unused")
	private static final String SERVER_TEST = "http://10.101.136.233:1234/api/v1/";
	@SuppressWarnings("unused")
	private static final String SERVER_PRODUCTION = "http://10.101.136.233:1234/api/v1/";
	private static final String SERVER_NOW = SERVER_PRODUCTION;

	public static INewsDao newOfflineInstance(final Context context) {
		return new NewsOfflineDao(prepareCacheHolder(context));
	}

	public static INewsDao newOnlineInstance(final Context context) { // 暫時性使用
		SharedPreferences prefs = context.getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
				Context.MODE_PRIVATE);
		String token = prefs.getString(NewsPreference.CONFIG_PROPERTY_ACCESS_TOKEN, null);
		ICacheHolder preferenceCacheHolder = prepareCacheHolder(context);

		return new MNewsDao(token, SERVER_NOW, preferenceCacheHolder);
	}

	public static INewsDao newInstance(final Context context) { // 暫時性使用
		SharedPreferences prefs = context.getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
				Context.MODE_PRIVATE);
		String token = prefs.getString(NewsPreference.CONFIG_PROPERTY_ACCESS_TOKEN, null);

		ICacheHolder preferenceCacheHolder = prepareCacheHolder(context);

		if (ConnectionDetector.isConnectingToInternet(context)) {
			return new MNewsDao(token, SERVER_NOW, preferenceCacheHolder);
		} else {
			return new NewsOfflineDao(preferenceCacheHolder);
		}
	}

	private static ICacheHolder prepareCacheHolder(final Context context) {
		return new ICacheHolder() {
			@Override
			public String getCache(int method, String key) {
				SharedPreferences prefs = context.getSharedPreferences(
						NewsPreference.PREFERENCE_NEWS_DAO_CACHE, Context.MODE_PRIVATE);
				String response = prefs.getString(method + ":" + key, null);
				LogUtil.d("cache:get by " + key + ",success:" + (response != null));
				return response;
			}

			@Override
			public void doCache(int method, String key, String response) {
				try {
					LogUtil.d("cache:push in " + key);
					SharedPreferences.Editor prefsEditor = context.getSharedPreferences(
							NewsPreference.PREFERENCE_NEWS_DAO_CACHE, Context.MODE_PRIVATE).edit();
					prefsEditor.putString(method + ":" + key, response);
					prefsEditor.commit();
				} catch (Exception ex) {
					LogUtil.e("cache:error occuring when save to cached preference", ex);
				}
			}
		};
	}

	public static INewsDao newInstance(String token) { // 暫時性使用

		return new MNewsDao(token, SERVER_NOW);
	}

}