package org.flow.boot.modeler.config;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

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

@RestControllerAdvice
public class WebGlobalException {

    private static final Logger log = LoggerFactory.getLogger(WebGlobalException.class);

    // 500异常
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Response<?> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException:{}", e);
        return Response.errorResponse(ErrorCode.UNKNOWN);

    }

    // 400异常
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

    // 400异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<?> handleBindException(ConstraintViolationException e) {
        log.error("参数绑定失败", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_400_VALIDATION);
        result.appendMessage(e.getMessage());
        return result;
    }

    // 400异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleBindException(IllegalArgumentException e) {
        log.error("参数绑定失败", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_400_VALIDATION);
        result.appendMessage(e.getMessage());
        return result;
    }

    // 403异常
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ServletException.class)
    public Response<?> handle403Exception(ServletException e) {
        log.error("401异常", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_400_VALIDATION);
        result.appendMessage(e.getMessage());
        return result;
    }

    // 404异常
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<?> handle404Exception(NoHandlerFoundException e) {
        log.error("请求没找到异常", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_404_NOT_FOUND);
        result.appendMessage(e.getMessage());
        return result;
    }

    // 405异常
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<?> handle405Exception(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_405_NOT_ALLOWED);
        result.appendMessage(e.getMessage());
        return result;
    }

    // 415异常
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response<?> handle415Exception(Exception e) {
        log.error("不支持当前媒体类型", e);
        Response<?> result = Response.errorResponse(ErrorCode.HTTP_415_MEDIA_TYPE);
        result.appendMessage(e.getMessage());
        return result;
    }
}
