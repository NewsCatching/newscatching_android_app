package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

public class MNewsMockDao extends BaseNewsDao {

	private String access_token;
	private String service_url;
	private ICacheHolder cacheHolder = null;

	public MNewsMockDao(String token, String service, ICacheHolder holder) {
		this.access_token = token;
		this.service_url = service;
		this.cacheHolder = holder;
	}

	public MNewsMockDao(String token, String service) {
		this(token, service, null);
	}

	@Override
	public ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return new ReturnMessage<String>(true,0 , "","access_token");
	}

	@Override
	public ReturnMessage<Boolean> ping(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * helper methods ------------------
	 */
}
