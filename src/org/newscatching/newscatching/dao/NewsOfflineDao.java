package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

public class NewsOfflineDao extends BaseNewsDao{
	private ICacheHolder cacheHolder = null;

	public NewsOfflineDao(ICacheHolder cacheHolder) {
		this.cacheHolder = cacheHolder;
	}

	
	private <T> ReturnMessage<T> noCacheReturnMessage() {
		return new ReturnMessage<T>(false, ReturnMessage.ERROR_NETWORK_NOT_CONNECTED, "網路目前尚未連線，所以無法存取資料。", null);
	}


	@Override
	public ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID) {
		return noCacheReturnMessage();
	}


	@Override
	public ReturnMessage<Boolean> ping(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return noCacheReturnMessage() ;
	}
	
}
