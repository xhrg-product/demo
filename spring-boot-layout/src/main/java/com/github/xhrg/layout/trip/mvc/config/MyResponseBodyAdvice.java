package com.github.xhrg.layout.trip.mvc.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.github.xhrg.layout.pojo.exp.AppException;
import com.github.xhrg.layout.pojo.util.JsonUtils;
import com.github.xhrg.layout.pojo.xxo.Response;

@ControllerAdvice("com.github.xhrg.layout.trip.mvc")
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// 如果是字符串，自己转为json的字符串形式
		if (returnType.getParameterType().isAssignableFrom(String.class)) {
			response.getHeaders().set("Content-Type", "application/json");
			return JsonUtils.toJson(Response.success(body));
		}

		if (body instanceof Response) {
			return body;
		}

		return Response.success(body);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleException(HttpServletRequest request, Exception ex) {
		Response resp = new Response();

		if (!(ex instanceof AppException)) {
			resp.setMsg("系统错误，请联系管理员");
			logger.error("handlerException, exception is ", ex);
			return resp;
		}

		AppException bizException = (AppException) ex;
		resp.setMsg(ex.getMessage());
		resp.setCode(bizException.getCode());
		return resp;
	}
}