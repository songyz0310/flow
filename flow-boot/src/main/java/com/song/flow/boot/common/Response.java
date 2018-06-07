package com.song.flow.boot.common;

public class Response {

	private int ecode;
	private String message;
	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static Response okResponse() {
		return okResponse(null);
	}

	public static Response okResponse(Object data) {
		Response resp = new Response();
		resp.setEcode(0);
		resp.setData(data);
		return resp;
	}

	public static Response errorResponse(ErrorCode errorCode) {
		Response resp = new Response();
		resp.setEcode(errorCode.getCode());
		resp.setMessage(errorCode.getMessage());
		return resp;
	}

	public static Response errorResponse(ErrorCode errorCode, Object data) {
		Response resp = errorResponse(errorCode);
		resp.setData(data);
		return resp;
	}

}
