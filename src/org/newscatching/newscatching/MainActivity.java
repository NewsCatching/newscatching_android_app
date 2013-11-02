package org.newscatching.newscatching;

import org.newscatching.newscatching.receiver.NetworkConnectedReceiver;
import org.newscatching.newscatching.util.ConnectionDetector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (ConnectionDetector.isConnectingToInternet(this)) {
			sendBroadcast(new Intent(this, NetworkConnectedReceiver.class));
		}
		Handler handlerTimer = new Handler();
		handlerTimer.postDelayed(new Runnable() {
			public void run() {

				Intent intent = new Intent(MainActivity.this, HotActivity.class);
				startActivity(intent);
				finish();
				
			}
		}, 2000);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
