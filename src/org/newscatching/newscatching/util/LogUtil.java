package org.newscatching.newscatching.util;

import org.newscatching.newscatching.NewsConstant;

import android.util.Log;

public class LogUtil {

	public static void e(String message, Throwable e) {
		Log.e(NewsConstant.LOG_TAG_ERROR, message, e);
	}

	public static void e(String message) {
		Log.e(NewsConstant.LOG_TAG_ERROR, message);
	}

	public static void d(String message) {
		Log.d(NewsConstant.LOG_TAG_DEBUG, message);
	}
}
