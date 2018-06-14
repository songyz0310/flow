package org.flow.boot.common;

public enum ErrorCode {

	UNKNOWN(-1, "未知异常"), //
	SUCCESS(0, "成功"), //
	
	BASE_REQUEST_400(1001, "缺少必要基础参数"), //

	// 基础异常
	PARAM_MISS(2001, "缺少必要基础参数"), //
	
	;

	// 系统未知异常

	private int code;
	private String message;

	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
