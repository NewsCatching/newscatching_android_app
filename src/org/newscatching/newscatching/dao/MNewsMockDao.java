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
		n.setContent("<!-- google_ad_section_start --> <p class=\"first\">（中央社記者戴雅真台北29日電）台北市政府今天公布星級商圈分級認證，美食聖地「寧夏商圈」和年輕人最愛去的「西門商圈」獲得3星級最高殊榮。</p> <p> 台北市長郝龍斌上午出席頒獎典禮表示，台北不但好吃，而且好玩，還有誠信的商家，這也是國內外觀光客認為台北市是友善城市的關鍵因素。</p> <p> 郝龍斌說，台北市不僅在最近萬事達卡公布的「全球最佳旅遊城市」調查報告擠進前20名，觀光消費金額更是華人圈當中最高的，顯示台北市的購物環境與競爭力持續向上提升。</p> <p> 今年的認證方式全面革新，採用星級評鑑制度，針對商圈組織的服務力、友善力、人氣力、特色力及創新力等5大績效指標評選。但郝龍斌認為，商圈所有業者的向心力和自律應該是第6項指標，這是他最欣賞的部分。</p> <p> 台北市商業處說，這次評選共選出寧夏和西門兩個3星級商圈，寧夏商圈有享譽國際的千歲宴，並自行建置友善廁所。西門商圈架構完整，商圈的青少年流行文化特色店家林立，還有各式旅店，吸引國內外觀光人潮。</p> <p> 天母商圈、台大公館商圈、後站批發商圈則獲得2星級評選。天母商圈有創意市集，新發展的寵物主題活動等；公館商圈有許多具有歷史文化的店家；後站商圈在整體環境進行美化後，提升遊逛順暢度。</p> <p> 商業處指出，另有8個商圈獲得1星級評選，分別是永康商圈、八德商圈、四平商圈、晴光商圈、承德汽車商圈、北門相機商圈、中華影音商圈及艋舺服飾商圈。西湖商圈、朝陽服飾材料商圈、文昌家具則是獲得潛力獎。1021029</p> <!-- google_ad_section_end --> ");
		n.setHot(true);
		n.setNewsID(newsID);
		n.setShareUrl("http://www.google.com.tw");
		n.setSource("yahoo~~~");
		n.setImageURL("http://t0.gstatic.com/images?q=tbn:ANd9GcShSs5VeFr7CwXwkxTY3Hl2kYo2CgFHawbbW1Ln-6Q6bZipWpj-Dg");
		n.setSupported(true);
		n.setTitle("台北商圈認證 寧夏西門獲3星");
		
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
