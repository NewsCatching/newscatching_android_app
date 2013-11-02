package org.newscatching.newscatching;

import android.content.Context;
import android.content.SharedPreferences;

public class NewsPreference {
	public static final String PREFERENCE_CONFIG = "perf_config";
	public static final String PREFERENCE_NEWS_DAO_CACHE = "perf_news_cache";

	public static final String CONFIG_PROPERTY_ACCESS_TOKEN = "config_prop_access_token";

	public static final String CONFIG_PROPERTY_REG_ID = "config_prop_reg";
	public static final String CONFIG_PROPERTY_REG_ID_UPLOADED = "config_prop_reg_uploaded";
	public static final String CONFIG_PROPERTY_ENABLE_GCM = "config_prop_gcm_enable";
	public static final String CONFIG_PROPERTY_DEVICE_ID = "config_prop_device_id";
	public static final String CONFIG_PROPERTY_NICKNAME = "config_prop_nick";

	public static SharedPreferences getConfig(Context context) {
		return context.getSharedPreferences(PREFERENCE_CONFIG, Context.MODE_PRIVATE);
	}
	
	public static SharedPreferences.Editor getConfigEditor(Context context) {
		return context.getSharedPreferences(PREFERENCE_CONFIG, Context.MODE_PRIVATE).edit();
	}

	public static String getNickname(Context context) {
		return getConfig(context).getString(CONFIG_PROPERTY_NICKNAME, null);
	}
	
	public static void saveNickname(Context context,String name) {
		getConfigEditor(context).putString(CONFIG_PROPERTY_NICKNAME, name).commit();
	}
}
