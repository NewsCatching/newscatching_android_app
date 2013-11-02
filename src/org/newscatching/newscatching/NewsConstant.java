package org.newscatching.newscatching;

import android.content.Context;
import android.content.SharedPreferences;

public class NewsConstant {
	public static final String GOOGLE_SENDER_ID = "489444839565";

	public static final String LOG_TAG_ERROR = "news-error";
	public static final String LOG_TAG_DEBUG = "news-debug";

	public static final boolean DEBUG_MODE = false;

	public static final String getDeviceID(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
				Context.MODE_PRIVATE);
		String deviceID = preferences.getString(NewsPreference.CONFIG_PROPERTY_DEVICE_ID, null);

		if (deviceID != null) {
			return deviceID;
		}

		SharedPreferences.Editor editor = preferences.edit();
		deviceID = java.util.UUID.randomUUID().toString();
		editor.putString(NewsPreference.CONFIG_PROPERTY_DEVICE_ID, deviceID);
		editor.commit();

		return deviceID;

	}

}
