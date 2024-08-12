package com.github.xhrg.web.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

	@RequestMapping("/http")
	public String all(HttpServletRequest request) {
		System.out.println(request.getRequestURL().toString());
		return "" + request.getRequestURL().toString();
	}
}
