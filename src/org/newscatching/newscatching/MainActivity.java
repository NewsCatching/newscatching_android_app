package org.newscatching.newscatching;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareGCMID();
	}

	private void prepareGCMID() {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		SharedPreferences preferences = getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
				Context.MODE_PRIVATE);
		String reg_id = preferences.getString(NewsPreference.CONFIG_PROPERTY_REG_ID, null);

		if (reg_id == null) {
			new AsyncTask<GoogleCloudMessaging, Void, String>() {
				@Override
				protected String doInBackground(GoogleCloudMessaging... params) {
					String msg = "";
					try {
						GoogleCloudMessaging message = params[0];
						String regid = message.register(NewsConstant.GOOGLE_SENDER_ID);

						SharedPreferences.Editor editor = getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
								Context.MODE_PRIVATE).edit();
						editor.putString(NewsPreference.CONFIG_PROPERTY_REG_ID, regid);
						editor.commit();
						sendGCMIDToServer(regid);

					} catch (IOException ex) {
						msg = "Error :" + ex.getMessage();
					}
					return msg;
				}

			}.execute(gcm, null, null);
		}
	}

	private void sendGCMIDToServer(String regid) {
		System.out.println(regid);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
