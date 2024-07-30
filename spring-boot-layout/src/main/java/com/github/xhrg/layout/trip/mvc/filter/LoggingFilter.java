package com.github.xhrg.layout.trip.mvc.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.github.xhrg.layout.pojo.util.StrUtils;
import com.github.xhrg.layout.trip.mvc.util.RequestUtils;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	private static final String TRACE_ID = "traceid";

	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		MDC.put(TRACE_ID, StrUtils.uuid());

		long start = System.currentTimeMillis();
		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
		// 这里需要打印请求来源ip等信息
		String uri = requestWrapper.getRequestURI();
		System.out.println(uri);
		if (isExclude(uri)) {
			filterChain.doFilter(requestWrapper, responseWrapper);
			responseWrapper.copyBodyToResponse();
			return;
		}
		logger.info("request ip is {}, request is {}", RequestUtils.getRequestIP(),
				new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
		filterChain.doFilter(requestWrapper, responseWrapper);
		long elapsedTime = System.currentTimeMillis() - start;
		logger.info("elapsedTime is {}, response is {}", elapsedTime,
				new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
		responseWrapper.copyBodyToResponse();
	}

	public boolean isExclude(String uri) {
		return false;
	}
}
