package org.newscatching.newscatching.viewmodel;

public class ReturnMessage<T> {

	public static final int ERROR_NETWORK_NOT_CONNECTED = -5;
	public static final int ERROR_DB_FAIL = 6;
	public static final int ERROR_VERIFY_FAIL = 5;

	public static final int SUCCESS = 200;
	public static final int SUCCESS_IN_CACHE = 201; 
	
	private boolean IsSuccess;
	private Integer StatusCode;
	private String ErrorMessage;
	private T Data;

	public ReturnMessage(boolean isSuccess, int errorCode, String errorMessage, T data) {
		super();
		IsSuccess = isSuccess;
		StatusCode = errorCode;
		ErrorMessage = errorMessage;
		Data = data;
	}

	public boolean isSuccess() {
		return IsSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		IsSuccess = isSuccess;
	}

	public int getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}

}
