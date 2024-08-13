package com.github.xhrg.demo.towbean;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping("/**")
	public String all(HttpServletRequest request) {
		System.out.println(request.getRequestURL().toString());
		return "" + request.getRequestURL().toString();
	}
}
