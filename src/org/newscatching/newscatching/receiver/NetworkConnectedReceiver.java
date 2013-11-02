package org.newscatching.newscatching.receiver;

import org.newscatching.newscatching.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectedReceiver extends BroadcastReceiver {
	private boolean _booted = false;
	private Context _context;

	@Override
	public void onReceive(Context context, Intent intent) {
		_context = context;
		LogUtil.d("Network connectivity change");

		ConnectivityManager connectivityManager = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();
		if (currentNetworkInfo != null && currentNetworkInfo.isConnected()) {
			if (!_booted) {
				onBoot();
				_booted = true;
			}
		}

	}

	private void onBoot() {
		_context.sendBroadcast(new Intent(_context, RegisterGCMReceiver.class));
	}

}