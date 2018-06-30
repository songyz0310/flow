package org.flow.boot.ticket.config;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.netflix.config.validation.ValidationException;

/**
 * 全局异常处理
 * 
 * @author Administrator
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// 运行时异常
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public Response<?> handleRuntimeException(RuntimeException e) {
		log.error("RuntimeException:{}", e);
		return Response.errorResponse(ErrorCode.UNKNOWN);

	}

	// 参数绑定异常
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Response<?> handleBindException(BindException e) {
		log.error("参数绑定失败", e);
		BindingResult bindingResult = e.getBindingResult();
		FieldError error = bindingResult.getFieldError();
		String field = error.getField();
		log.error(field);
		String code = error.getDefaultMessage();
		Response<?> result = Response.errorResponse(ErrorCode.HTTP_400_BIND);
		result.appendMessage(code);
		return result;
	}

	// 参数验证异常
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public Response<?> handleValidationException(ValidationException e) {
		log.error("参数验证失败", e);
		Response<?> result = Response.errorResponse(ErrorCode.HTTP_400_VALIDATION);
		result.appendMessage(e.getMessage());
		return result;
	}

	// 请求没找到异常
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Response<?> handleHttpRequestMethodNotSupportedException(NoHandlerFoundException e) {
		log.error("请求没找到异常", e);
		Response<?> result = Response.errorResponse(ErrorCode.HTTP_404_NOT_FOUND);
		result.appendMessage(e.getMessage());
		return result;
	}

	// 请求类型不被允许
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法", e);
		Response<?> result = Response.errorResponse(ErrorCode.HTTP_405_NOT_ALLOWED);
		result.appendMessage(e.getMessage());
		return result;
	}

	// 不支持的媒体类型
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Response<?> handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("不支持当前媒体类型", e);
		Response<?> result = Response.errorResponse(ErrorCode.HTTP_415_MEDIA_TYPE);
		result.appendMessage(e.getMessage());
		return result;
	}

}
