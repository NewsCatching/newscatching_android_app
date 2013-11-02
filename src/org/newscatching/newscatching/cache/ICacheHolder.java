package org.newscatching.newscatching.cache;

public interface ICacheHolder {

	public void doCache(int method, String key, String response);

	public String getCache(int method, String key);
}
