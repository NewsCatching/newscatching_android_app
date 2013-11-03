package org.newscatching.newscatching.dao;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.newscatching.newscatching.NewsConstant;
import org.newscatching.newscatching.activity.NewsActivity;
import org.newscatching.newscatching.activity.NewsListActivity;
import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.util.ArrayUtil;
import org.newscatching.newscatching.util.HttpUtil;
import org.newscatching.newscatching.util.LogUtil;
import org.newscatching.newscatching.viewmodel.HotNews;
import org.newscatching.newscatching.viewmodel.News;
import org.newscatching.newscatching.viewmodel.NewsDetails;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

public class MNewsDao extends BaseNewsDao {

	private String access_token;
	private String service_url;
	private ICacheHolder cacheHolder = null;

	public MNewsDao(String token, String service, ICacheHolder holder) {
		this.access_token = token;
		this.service_url = service;
		this.cacheHolder = holder;
	}

	public MNewsDao(String token, String service) {
		this(token, service, null);
	}

	// TODO:attach data

	@SuppressWarnings("unused")
	private <K, T> ReturnMessage<T> doRequest(String url) {
		return doGET(url, null, null);
	}

	@SuppressWarnings("unused")
	private <K, T> ReturnMessage<T> doRequest(String url, Map<String, Object> parameters) {
		return doGET(url, parameters, null);
	}

	@SuppressWarnings("unchecked")
	private <K, T> ReturnMessage<T> doRequest(int method, String url, Map<String, Object> paramters,
			DataConverter<K, T> converter, boolean cache, String cache_key) {
		String response = null;
		try {
			// if (this.access_token == null &&
			// url.indexOf("DoAndroidActivating") == -1) {
			// return new ReturnMessage<T>(false, -3, "尚未通過驗證所以無法存取資料", null);
			// }
			if (method == METHOD_GET) {
				StringBuffer param = new StringBuffer();
				if (paramters != null) {

					for (Map.Entry<String, Object> entry : paramters.entrySet()) {
						if (ArrayUtil.isArray(entry.getValue())) {
							// Note: required to be String[]
							String[] values = (String[]) entry.getValue();
							for (String str : values) {
								param.append("&" + entry.getKey() + "[]=" + URLEncoder.encode(str, "UTF-8"));
							}
						} else if (entry instanceof List) {
							for (Object value : (List<?>) entry) {
								param.append("&" + entry.getKey() + "[]="
										+ URLEncoder.encode(String.valueOf(value), "UTF-8"));
							}
						} else {
							param.append("&" + entry.getKey() + "=" + String.valueOf(entry.getValue()));
						}
					}
					if (NewsConstant.DEBUG_MODE) {
						LogUtil.d("param result:" + param);
					}
				}
				response = HttpUtil.doGET(this.service_url + "/" + url + "?access_token=" + this.access_token
						+ param.toString());

			} else if (method == METHOD_POST) {
				if (paramters == null) {
					paramters = new HashMap<String, Object>();
				}
				paramters.put("access_token", this.access_token);
				response = HttpUtil.doPost(this.service_url + "/" + url, paramters);
			} else if (method == METHOD_MULTIPART_POST) {
				if (paramters == null) {
					paramters = new HashMap<String, Object>();
				}
				paramters.put("access_token", this.access_token);
				response = HttpUtil.doMultiPartPost(this.service_url + "/" + url, paramters);
			} else {
				throw new IllegalArgumentException("Only POST/GET method allowed.");
			}
			if (NewsConstant.DEBUG_MODE) {
				LogUtil.d("response:" + response);
			}
			JSONObject jsonObject = new JSONObject(response);

			T converted = null;
			if (!jsonObject.isNull("data")) {
				if (converter != null) {
					converted = converter.ConvertTo((K) jsonObject.get("data"));
				} else {
					converted = (T) jsonObject.get("data");
				}
			}

			boolean isSuccess = jsonObject.getInt("status") == 0;

			if (isSuccess && cache && this.cacheHolder != null && cache_key != null) {
				this.cacheHolder.doCache(method, cache_key, response);
			}

			return new ReturnMessage<T>(isSuccess, jsonObject.getInt("status"), jsonObject.getString("message"),
					converted);

		} catch (MalformedURLException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (JSONException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.e(e.getMessage(), e);
		}
		return new ReturnMessage<T>(false, -2, "Not able to parse result:" + response, null);
	}

	private <K, T> ReturnMessage<T> doCacheGET(String url, Map<String, Object> params,
			DataConverter<K, T> converter, boolean cache, String cache_key) {
		return doRequest(METHOD_GET, url, params, converter, cache, cache_key);
	}

	private <K, T> ReturnMessage<T> doGET(String url, Map<String, Object> params, DataConverter<K, T> converter) {
		return doRequest(METHOD_GET, url, params, converter, false, null);
	}

	private <K, T> ReturnMessage<T> doPOST(String url, Map<String, Object> paramters, DataConverter<K, T> converter) {
		return doRequest(METHOD_POST, url, paramters, converter, false, null);
	}

	private <K, T> ReturnMessage<T> doMultiPartPost(String url, Map<String, Object> paramters,
			DataConverter<K, T> converter) {
		return doRequest(METHOD_MULTIPART_POST, url, paramters, converter, false, null);
	}

	@Override
	public ReturnMessage<String> doAddAndroidGCMID(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMessage<Boolean> ping(String deviceID, String regID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMessage<List<HotNews>> getHotNews() {
		return doCacheGET("news/hotests", null, new DataConverter<JSONArray, List<HotNews>>() {
			@Override
			public List<HotNews> ConvertTo(JSONArray input) throws Exception {
				List<HotNews> list = new ArrayList<HotNews>();

				for (int i = 0; i < input.length(); ++i) {
					JSONObject obj = input.getJSONObject(i);
					// TODO: replace image url
					list.add(new HotNews(obj.getString("id"), obj.getString("title"), obj.getString("ogImage")));
				}

				return list;
			}
		}, true, "getHotNews");
	}

	@Override
	public ReturnMessage<NewsDetails> getNews(String newsID) {
		return doCacheGET("news/read/" + newsID, null, new DataConverter<JSONObject, NewsDetails>() {
			@Override
			public NewsDetails ConvertTo(JSONObject input) throws Exception {
				JSONObject jsonNews = input.getJSONObject("news");
				News n = new News();
				n.setNewsID(jsonNews.getString("id"));
				n.setTitle(jsonNews.getString("title"));
				n.setContent(jsonNews.getString("body"));
				n.setImageURL(jsonNews.getString("picUrl"));
				n.setSource(jsonNews.getString("referral"));
				NewsDetails nd = new NewsDetails();
				nd.setNews(n);

				return nd;
			}
		}, true, "getNews/" + newsID);
	}

	@Override
	public ReturnMessage<Object> addNewTalk(String newsID, String talk, String nick) {
		// TODO Auto-generated method stub
		return new ReturnMessage<Object>(false, 0, "", null);
	}

	@Override
	public ReturnMessage<Object> addNewReport(String newsID, String nick, String url, String comment) {
		// TODO Auto-generated method stub
		return new ReturnMessage<Object>(false, 0, "", null);
	}

	@Override
	public ReturnMessage<List<News>> getNewsList(int type, String q) {

		String api_url = "news/report";

		if (type == NewsListActivity.TYPE_INTERNET) {
			api_url = "news/report";
		} else if (type == NewsListActivity.TYPE_HOT) {
			api_url = "news/hot";
		} else if (type == NewsListActivity.TYPE_NEWS) {
			api_url = "news/list";
		}

		HashMap<String, Object> params = null;

		if (q != null) {
			params = new HashMap<String, Object>();
			params.put("q", URLEncoder.encode(q));
		}

		return doCacheGET(api_url, params, new DataConverter<JSONArray, List<News>>() {
			@Override
			public List<News> ConvertTo(JSONArray input) throws Exception {

				List<News> news = new ArrayList<News>();

				for (int i = 0; i < input.length(); ++i) {
					JSONObject jsonNews = input.getJSONObject(i);
					News n = new News();
					n.setNewsID(jsonNews.getString("id"));

					n.setTitle(jsonNews.getString("title"));
					// n.setContent(jsonNews.getString("body"));
					n.setImageURL(jsonNews.getString("ogImage"));
					n.setSource(jsonNews.getString("referral"));
					n.setSupported("1".equals(jsonNews.getString("isSupport")));
					news.add(n);
				}

				return news;
			}
		}, true, "getNewsList/" + type);

	}
	/*
	 * helper methods ------------------
	 */
}
