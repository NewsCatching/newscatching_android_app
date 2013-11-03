package org.newscatching.newscatching.util;

import android.app.ProgressDialog;

public class ProgressbarLoadingHandler implements LoadingHandler {
	private ProgressDialog _progressbar;
	private int _showCount = 0;

	public ProgressbarLoadingHandler(ProgressDialog progressbar) {
		_progressbar = progressbar;
	}

	@Override
	public void dismissProgressBar() {
		_showCount--;
		if (_progressbar != null && _showCount == 0) {
			_progressbar.dismiss();
		}
	}

	@Override
	public void showProgressBar() {
		_showCount++;
		if (_progressbar != null && _showCount == 1) {
			_progressbar.setCanceledOnTouchOutside(false);
			_progressbar.show();
		}
	}

	@Override
	public void setMessage(String message) {
		if (_progressbar != null) {
			_progressbar.setMessage(message);
		}
	}
}
