package org.newscatching.newscatching.observer;

import android.app.Activity;

public interface ActivityDestroyObserver {

	public void registerOnDestroy(ActivityEndAction action);

	public Activity getActivity();
}
