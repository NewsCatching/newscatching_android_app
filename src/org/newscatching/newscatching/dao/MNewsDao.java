package org.newscatching.newscatching.dao;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.newscatching.newscatching.NewsConstant;
import org.newscatching.newscatching.cache.ICacheHolder;
import org.newscatching.newscatching.util.ArrayUtil;
import org.newscatching.newscatching.util.HttpUtil;
import org.newscatching.newscatching.viewmodel.ReturnMessage;

import android.util.Log;

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
			if (this.access_token == null && url.indexOf("DoAndroidActivating") == -1) {
				return new ReturnMessage<T>(false, -3, "尚未通過驗證所以無法存取資料", null);
			}
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
						Log.d("mcall-debug", "param result:" + param);
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
				Log.d("mcall-debug", "response:" + response);
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

			if (jsonObject.getBoolean("isSuccess") && cache && this.cacheHolder != null && cache_key != null) {
				this.cacheHolder.doCache(method, cache_key, response);
			}

			return new ReturnMessage<T>(jsonObject.getBoolean("isSuccess"), jsonObject.getInt("errorCode"),
					jsonObject.getString("errorMessage"), converted);

		} catch (MalformedURLException e) {
			Log.e("mcall-error", e.getMessage(), e);
		} catch (JSONException e) {
			Log.e("mcall-error", e.getMessage(), e);
		} catch (Exception e) {
			Log.e("mcall-error", e.getMessage(), e);
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

	/*
	 * helper methods ------------------
	 */
}
