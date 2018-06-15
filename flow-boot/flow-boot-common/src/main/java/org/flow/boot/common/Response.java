package org.flow.boot.common;

public class Response<T> {

	private int ecode;
	private String message;
	private T data;

	public int getEcode() {
		return ecode;
	}

	public void setEcode(int ecode) {
		this.ecode = ecode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> Response<T> okResponse() {
		return okResponse(null);
	}

	public static <T> Response<T> okResponse(T data) {
		Response<T> resp = new Response<T>();
		resp.setEcode(0);
		resp.setData(data);
		return resp;
	}

	public static <T> Response<T> errorResponse(ErrorCode errorCode) {
		Response<T> resp = new Response<T>();
		resp.setEcode(errorCode.getCode());
		resp.setMessage(errorCode.getMessage());
		return resp;
	}

	public static <T> Response<T> errorResponse(ErrorCode errorCode, T data) {
		Response<T> resp = errorResponse(errorCode);
		resp.setData(data);
		return resp;
	}

}
