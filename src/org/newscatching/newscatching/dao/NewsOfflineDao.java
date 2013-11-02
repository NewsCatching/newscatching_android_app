package org.newscatching.newscatching.dao;

import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

public class NewsOfflineDao extends BaseNewsDao{
	private ICacheHolder cacheHolder = null;

	public NewsOfflineDao(ICacheHolder cacheHolder) {
		this.cacheHolder = cacheHolder;
	}

	
	private <T> ReturnMessage<T> noCacheReturnMessage() {
		return new ReturnMessage<T>(false, ReturnMessage.ERROR_NETWORK_NOT_CONNECTED, "�����ثe�|���s�u�A�ҥH�L�k�s����ơC", null);
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
