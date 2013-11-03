package org.newscatching.newscatching.dao;

import java.util.ArrayList;
import java.util.List;

import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.viewmodel.HotNews;
import org.newscatching.newscatching.viewmodel.News;
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
			list.add(new HotNews(""+i,"test "+i,"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn1/211229_1403951219_1146935976_q.jpg"));
		}
		list.get(3).setImageUrl(null);
		list.add(new HotNews(""+5,"test 5","https://fbcdn-profile-a.akamaihd.net/hprofile-ak-prn2/s32x32/203402_1521110015_1215854080_q.jpg"));
		
		return new ReturnMessage<List<HotNews>>(true,0,"",list);
	}

	@Override
	public ReturnMessage<News> getNews(String newsID) {
		News n = new News();
		n.setContent("<!-- google_ad_section_start --> <p class=\"first\">�]�������O�������u�x�_29��q�^�x�_���F�����Ѥ����P�ŰӰ���Ż{�ҡA�����t�a�u��L�Ӱ�v�M�~���H�̷R�h���u����Ӱ�v��o3�P�ų̰���a�C</p> <p> �x�_�����q�s�y�W�ȥX�u�{����§��ܡA�x�_�����n�Y�A�ӥB�n���A�٦��۫H���Ӯa�A�o�]�O�ꤺ�~�[���Ȼ{���x�_���O�͵�����������]���C</p> <p> �q�s�y���A�x�_�����Ȧb�̪�U�ƹF�d�������u���y�̨ήȹC�����v�լd���i���i�e20�W�A�[�����O���B��O�ؤH����̰����A��ܥx�_�����ʪ����һP�v���O����V�W���ɡC</p> <p> ���~���{�Ҥ覡�������s�A�ĥάP�ŵ�Ų��סA�w��Ӱ��´���A�ȤO�B�͵��O�B�H��O�B�S��O�γзs�O��5�j�Z�ī��е���C���q�s�y�{���A�Ӱ�Ҧ��~�̪��V�ߤO�M�۫����ӬO��6�����СA�o�O�L�̪Y�઺�����C</p> <p> �x�_���ӷ~�B���A�o������@��X��L�M������3�P�ŰӰ�A��L�Ӱ馳���A��ڪ��d���b�A�æۦ�ظm�͵��Z�ҡC����Ӱ�[�c����A�Ӱ骺�C�֦~�y���ƯS�⩱�a�L�ߡA�٦��U���ȩ��A�l�ްꤺ�~�[���H��C</p> <p> �ѥ��Ӱ�B�x�j���]�Ӱ�B�᯸��o�Ӱ�h��o2�P�ŵ���C�ѥ��Ӱ馳�зN�����A�s�o�i���d���D�D���ʵ��F���]�Ӱ馳�\�h�㦳���v��ƪ����a�F�᯸�Ӱ�b�������Ҷi����ƫ�A���ɹC�}���Z�סC</p> <p> �ӷ~�B���X�A�t��8�ӰӰ���o1�P�ŵ���A���O�O�ñd�Ӱ�B�K�w�Ӱ�B�|���Ӱ�B�����Ӱ�B�Ӽw�T���Ӱ�B�_���۾��Ӱ�B���ؼv���Ӱ�λS�U�A���Ӱ�C���Ӱ�B�¶��A�����ưӰ�B����a��h�O��o��O���C1021029</p> <!-- google_ad_section_end --> ");
		n.setHot(true);
		n.setNewsID(newsID);
		n.setShareUrl("http://www.google.com.tw");
		n.setSource("yahoo~~~");
		n.setImageURL("http://t0.gstatic.com/images?q=tbn:ANd9GcShSs5VeFr7CwXwkxTY3Hl2kYo2CgFHawbbW1Ln-6Q6bZipWpj-Dg");
		n.setSupported(true);
		n.setTitle("�x�_�Ӱ�{�� ��L�����3�P");
		
		return new ReturnMessage<News>(true,0,"",n);
	}

	@Override
	public ReturnMessage<Object> addNewTalk(String newsID, String talk, String nick) {
		return new ReturnMessage<Object>(false,0,"",null);

	}

	@Override
	public ReturnMessage<Object> addNewReport(String newsID,String nick, String url, String comment) {
		// TODO Auto-generated method stub
		return new ReturnMessage<Object>(false,0,"",null);

	}
	
	

	/*
	 * helper methods ------------------
	 */
}
