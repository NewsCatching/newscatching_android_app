package org.newscatching.newscatching.receiver;

import org.newscatching.newscatching.NewsConstant;
import org.newscatching.newscatching.NewsPreference;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMBroadcastReceiver extends BroadcastReceiver {

	private static final String TYPE_ENABLE_GCM = "2";
	private static final String TYPE_NEW_MESSAGE = "1";

	@Override
	public void onReceive(final Context context, Intent intent) {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
		String messageType = gcm.getMessageType(intent);
		if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
			Log.e(NewsConstant.LOG_TAG_ERROR, "Send error: " + intent.getExtras().toString());
		} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
			Log.e(NewsConstant.LOG_TAG_ERROR, "Send error: " + intent.getExtras().toString());
		} else {
			Bundle extra = intent.getExtras();
			String type = extra.getString("message");

			Log.d(NewsConstant.LOG_TAG_DEBUG, "message:new message coming");

			if (TYPE_ENABLE_GCM.equals(type)) {
				// DO Nothing due to we will set GCM enable below
			}

			// set GCM enable
			SharedPreferences props = context.getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
					Context.MODE_PRIVATE);
			props.edit().putBoolean(NewsPreference.CONFIG_PROPERTY_ENABLE_GCM, true).commit();
		}
		setResultCode(Activity.RESULT_OK);
	}
}