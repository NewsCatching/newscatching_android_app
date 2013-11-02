package org.newscatching.newscatching.util;

public class ArrayUtil {
	public static boolean isArray(final Object obj) {
		return obj!=null && obj.getClass().isArray();
	}
}
