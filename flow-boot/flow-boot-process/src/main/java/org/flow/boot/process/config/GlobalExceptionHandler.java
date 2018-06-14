package org.flow.boot.process.config;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.flow.boot.common.ErrorCode;
import org.flow.boot.common.Response;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(RuntimeException.class)
	public Response deployForm(HttpServletRequest req, Exception ex) {

		return Response.errorResponse(ErrorCode.UNKNOWN);

	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public Response handleBindException(BindException e) {
		log.error("参数绑定失败", e);
		BindingResult bindingResult = e.getBindingResult();
		FieldError error = bindingResult.getFieldError();
		String field = error.getField();
		log.error(field);
		String code = error.getDefaultMessage();
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(code);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Response handleServiceException(ConstraintViolationException e) {
		log.error("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(message);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public Response handleValidationException(ValidationException e) {
		log.error("参数验证失败", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

	/**
	 * 404 - Not Found
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHandlerFoundException.class)
	public Response handleHttpRequestMethodNotSupportedException(NoHandlerFoundException e) {
		log.error("没找到", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}
	/**
	 * 405 - Method Not Allowed
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Response handleHttpMediaTypeNotSupportedException(Exception e) {
		log.error("不支持当前媒体类型", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ServiceException.class)
	public Response handleServiceException(ServiceException e) {
		log.error("业务逻辑异常", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Response handleException(Exception e) {
		log.error("通用异常", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

	/**
	 * 操作数据库出现异常:名称重复，外键关联
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Response handleException(DataIntegrityViolationException e) {
		log.error("操作数据库出现异常:", e);
		Response result = Response.errorResponse(ErrorCode.BASE_REQUEST_400);
		result.setMessage(e.getMessage());
		return result;
	}

}
