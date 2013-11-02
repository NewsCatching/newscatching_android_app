package org.newscatching.newscatching.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewUtils {
	public static TextView createTextView(Context context, String label) {
		TextView textview = new TextView(context);
		textview.setText(label);
		return textview;
	}

	public static TextView createTextView(Context context, String label, int padding_left) {
		TextView textview = new TextView(context);
		textview.setText(label);
		textview.setPadding(padding_left, 0, 0, 0);
		return textview;
	}

	public static void alert(Context context, String title, String content, OnClickListener callback) {
		Builder myAlertDialog = new AlertDialog.Builder(context);
		myAlertDialog.setTitle(title);
		myAlertDialog.setMessage(content);

		if (callback == null) {
			callback = new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			};
		}
		myAlertDialog.setPositiveButton("OK", callback);
		myAlertDialog.show();
	}

	public static void confirm(Context context, String title, String content, OnClickListener ok_callback,
			OnClickListener no_callback) {
		Builder myAlertDialog = new AlertDialog.Builder(context);
		myAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
		myAlertDialog.setTitle(title);
		myAlertDialog.setMessage(content);

		DialogInterface.OnClickListener empty = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		};
		if (ok_callback == null) {
			ok_callback = empty;
		}
		if (no_callback == null) {
			no_callback = empty;
		}
		myAlertDialog.setNegativeButton("½T©w", ok_callback);
		myAlertDialog.setPositiveButton("¨ú®ø", no_callback);
		myAlertDialog.show();

	}

	public static Intent viewOnMap(String address) {
		try {
			return new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:0,0?q=%s",
					URLEncoder.encode(address, "utf-8"))));
		} catch (UnsupportedEncodingException e) {
			Log.e("mcall-error", e.getMessage(), e);
			throw new IllegalStateException(e);
		}
	}

	public static Intent viewOnMap(String lat, String lng) {
		return new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("geo:%s,%s", lat, lng)));
	}
	
	public static void deepOnLongClickBinder(View view, View.OnLongClickListener listener) {
		if (view == null) {
			return;
		}
		view.setOnLongClickListener(listener);
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
				View v = ((ViewGroup) view).getChildAt(i);
				if (v instanceof ViewGroup) {
					deepOnLongClickBinder(v, listener);
				} else {
					v.setOnLongClickListener(listener);
				}
			}
		}

	}

	public static void deepOnClickBinder(View view, View.OnClickListener listener) {
		if (view == null) {
			return;
		}
		view.setOnClickListener(listener);
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); ++i) {
				View v = ((ViewGroup) view).getChildAt(i);
				if (v instanceof ViewGroup) {
					deepOnClickBinder((ViewGroup) v, listener);
				} else {
					v.setOnClickListener(listener);
				}
			}
		}

	}

}
