package org.newscatching.newscatching;

import java.util.ArrayList;
import java.util.List;

import org.newscatching.newscatching.observer.ActivityDestroyObserver;
import org.newscatching.newscatching.observer.ActivityEndAction;
import org.newscatching.newscatching.util.LoadingHandler;
import org.newscatching.newscatching.util.LogUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class BaseActivity extends Activity implements ActivityDestroyObserver, LoadingHandler {
	private List<ActivityEndAction> actions;
	private int showIndeterminateProgressBar = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		showIndeterminateProgressBar = 0; 
	}

	@Override
	public void setMessage(String message) {

	}

	@Override
	protected void onStart() {
		super.onStart();
		// View view = findViewById(R.id.head_progress);
		// if (view != null) {
		// view.setVisibility(View.GONE);
		// LogUtil.d("activity_loading:init:" + showIndeterminateProgressBar);
		// }
	}

	public void showProgressBar() {
		showIndeterminateProgressBar++;
		// View view = findViewById(R.id.head_progress);
		// if (view != null) {
		// view.setVisibility(View.VISIBLE);
		// }
		// LogUtil.d("activity_loading:++:" + showIndeterminateProgressBar);
	}

	public void dismissProgressBar() {
		showIndeterminateProgressBar--;
		if (showIndeterminateProgressBar == 0) {
			// View view = findViewById(R.id.head_progress);
			//
			// if (view != null) {
			// view.setVisibility(View.GONE);
			// }

		}
		LogUtil.d("activity_loading:--:" + showIndeterminateProgressBar);

	}

	@SuppressWarnings("unchecked")
	protected <T extends View> T _findViewById(int id) {
		return (T) findViewById(id);
	}

	@Override
	public void registerOnDestroy(ActivityEndAction action) {
		if (actions == null)
			actions = new ArrayList<ActivityEndAction>();
		actions.add(action);
	}

	protected void onDestroy() {
		super.onDestroy();
		if (actions != null) {
			for (ActivityEndAction action : actions) {
				try {
					action.execute(this);
				} catch (Exception ex) {
					LogUtil.e("error", ex);
				}
			}
			actions.clear();
			actions = null;
		}
	}

	@Override
	public Activity getActivity() {
		return this;
	}
}
