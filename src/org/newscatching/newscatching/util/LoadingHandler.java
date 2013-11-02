package org.newscatching.newscatching.util;


public interface LoadingHandler {
	public void showProgressBar() ;

	public void setMessage(String message);
	public void dismissProgressBar() ;
}
