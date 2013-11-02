package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.NewsPreference;
import org.newscatching.newscatching.cache.ICacheHolder;

import android.content.Context;
import android.content.SharedPreferences;

abstract public class BaseMcallDao implements INewsDao {
	@SuppressWarnings("unused")
	private static final String SERVER_TEST = "http://test.5945.tw/apiv1";
	// public static final String SERVER_TEST = "http://dev.5945.tw/5945/apiv1";
	@SuppressWarnings("unused")
	private static final String SERVER_PRODUCTION = "http://5945.tw/apiv1";
	private static final String SERVER_NOW = SERVER_PRODUCTION;


}