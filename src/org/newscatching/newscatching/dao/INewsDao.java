package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.viewmodel.ReturnMessage;

public interface INewsDao {
	public final int METHOD_GET = 0;
	public final int METHOD_POST = 1;
	public final int METHOD_MULTIPART_POST = 2;

	public static final int SHEET_TYPE_ALL = 0;
	public static final int SHEET_TYPE_WORKING = 1;
	public static final int SHEET_TYPE_CLOSE = 1;

	public abstract ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID);

	public abstract ReturnMessage<Boolean> ping(String deviceID, String regID);

}