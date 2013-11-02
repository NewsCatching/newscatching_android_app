package org.newscatching.newscatching.dao;

import java.util.ArrayList;
import java.util.List;

import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.HotNews;
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

	@Override
	public ReturnMessage<List<HotNews>> getHotNews() {
		ArrayList<HotNews> list = new ArrayList<HotNews>();
		
		for(int i = 0 ; i < 5;++i){
			list.add(new HotNews(i,"test "+i,"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/211229_1403951219_1146935976_q.jpg"));
		}
		list.get(3).setImageUrl(null);
		list.add(new HotNews(5,"test 5","https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/s32x32/203402_1521110015_1215854080_q.jpg"));
		
		return new ReturnMessage<List<HotNews>>(true,0,"",list);
	}
	

	/*
	 * helper methods ------------------
	 */
}
