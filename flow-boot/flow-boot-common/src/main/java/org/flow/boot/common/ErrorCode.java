package org.flow.boot.common;

public enum ErrorCode {

	UNKNOWN(-1, "未知异常"), //
	SUCCESS(0, "成功"), //

	// http 异常
	HTTP_400_BIND(1001, "【MVC】参数绑定异常，"), //
	HTTP_400_VALIDATION(1002, "【MVC】参数违反约束，"), //
	HTTP_404_NOT_FOUND(1003, "【MVC】请求地址没找到，"), //
	HTTP_405_NOT_ALLOWED(1004, "【MVC】请求类型不被允许，"), //
	HTTP_415_MEDIA_TYPE(1005, "【MVC】不支持的媒体类型，"), //

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
