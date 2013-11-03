package org.newscatching.newscatching.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class LoadingAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private LoadingHandler _dialog;
	private String _wording = null;
	private boolean _hideDialog = false;

	public boolean isHideDialog() {
		return _hideDialog;
	}

	public LoadingAsyncTask<Params, Progress, Result> setHideDialog(boolean hideDialog) {
		this._hideDialog = hideDialog;
		return this;
	}

	public LoadingHandler getLoadingHandler() {
		return _dialog;
	}

	/**
	 * Note that null dialog will be ignored.
	 * 
	 * @param dialog
	 * @return
	 */
	public LoadingAsyncTask<Params, Progress, Result> updateLoadingHandler(LoadingHandler dialog) {
		if (dialog != null) {
			_dialog = dialog;
		}
		return this;
	}

	public LoadingAsyncTask(Context context) {
		this(context, "Loading", null);
	}

	public LoadingAsyncTask(Context context, int wording_resourceID) {
		this(context, context.getResources().getString(wording_resourceID), null);
	}

	public LoadingAsyncTask(Context context, int wording_resourceID, LoadingHandler dialog) {
		this(context, context.getResources().getString(wording_resourceID), dialog);
	}

	public LoadingHandler preparedDefaultHandler(final Context context) {
		return new ProgressbarLoadingHandler(new ProgressDialog(context));
	}

	public LoadingAsyncTask(Context context, String wording, LoadingHandler dialog) {
		this._wording = wording;
		if (dialog != null) {
			_dialog = dialog;
		} else {
			_dialog = preparedDefaultHandler(context);
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (!_hideDialog) {
			_dialog.setMessage(_wording);
			_dialog.showProgressBar();
		}
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (!_hideDialog) {
			_dialog.dismissProgressBar();
		}
	}
}