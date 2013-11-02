package org.newscatching.newscatching.receiver;

import java.io.IOException;

import org.newscatching.newscatching.NewsConstant;
import org.newscatching.newscatching.NewsPreference;
import org.newscatching.newscatching.dao.BaseNewsDao;
import org.newscatching.newscatching.dao.INewsDao;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class RegisterGCMReceiver extends BroadcastReceiver {
	private Context _context;

	@Override
	public void onReceive(Context context, Intent intent) {
		_context = context;
		setupGCM();
	}

	private void setupGCM() {

		// Make sure the app is registered with GCM and with the server
		// SharedPreferences prefs =
		// _context.getSharedPreferences(McallPerferences.PERFERENCE_MCALL,
		// Context.MODE_PRIVATE);

		// String regid =
		// prefs.getString(McallPerferences.MCALL_PROPERTY_REG_ID, null);

		// If there is no registration ID, the app isn't registered.
		// Call registerBackground() to register it.
		registerBackground();

	}

	private void sendGCMIDToServer(String id) {
		new AsyncTask<String, Void, ReturnMessage<String>>() {
			public ReturnMessage<String> doInBackground(String... params) {
				INewsDao dao = BaseNewsDao.newInstance(_context);
				return dao.doAddAndroidGCMID(NewsConstant.getDeviceID(_context), params[0]);
			};

			protected void onPostExecute(ReturnMessage<String> result) {
				if (!result.isSuccess()) {
					Log.e("mcall-error", "Can't reg id to server side.");
					return;
				}
				SharedPreferences.Editor editor = _context.getSharedPreferences(NewsPreference.PREFERENCE_CONFIG,
						Context.MODE_PRIVATE).edit();
				editor.putString(NewsPreference.CONFIG_PROPERTY_ACCESS_TOKEN, result.getData());
				editor.putBoolean(NewsPreference.CONFIG_PROPERTY_REG_ID_UPLOADED, true);
				editor.commit();
			};
		}.execute(id);
	}

	private void registerBackground() {
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(_context);
		new AsyncTask<GoogleCloudMessaging, Void, String>() {
			@Override
			protected String doInBackground(GoogleCloudMessaging... params) {
				String msg = "";
				try {
					GoogleCloudMessaging message = params[0];
					String regid = message.register(NewsConstant.GOOGLE_SENDER_ID);

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that will echo
					// back
					// the message using the 'from' address in the message.

					// Save the regid for future use - no need to register
					// again.
					SharedPreferences.Editor editor = _context.getSharedPreferences(
							NewsPreference.PREFERENCE_CONFIG, Context.MODE_PRIVATE).edit();
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