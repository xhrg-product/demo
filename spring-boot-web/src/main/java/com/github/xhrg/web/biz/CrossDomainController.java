package com.github.xhrg.web.biz;

import java.time.Duration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置hosts
 * 
 * 127.0.0.1 home.com
 * 
 * 127.0.0.1 out.com
 */
@RestController
public class CrossDomainController {

	@RequestMapping("/cross/home")
	public String home(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("home请求来了");
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				System.out.println(c.getName() + ":" + c.getValue());
			}
		}
		Cookie cookie = new Cookie("home-key", "home-value");
		response.addCookie(cookie);
		return "home";
	}

	@RequestMapping("/cross/out")
	public String out(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("out请求来了");
		response.addHeader("Access-Control-Allow-Origin", "http://home.com:8080");
		response.addHeader("Access-Control-Allow-Methods", "GET,POST");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				System.out.println(c.getName() + ":" + c.getValue());
			}
		}
		ResponseCookie cookie = ResponseCookie.from("out-key", "out-value").httpOnly(false).secure(false)
				// .domain("localhost")
				.path("/").maxAge(Duration.ofHours(24)).sameSite("Lax").build();
		response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		return "out";
	}

}
