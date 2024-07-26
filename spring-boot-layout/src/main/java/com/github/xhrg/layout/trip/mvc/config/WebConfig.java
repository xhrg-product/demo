package com.github.xhrg.layout.trip.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//	@Override
//	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.add(0, new MappingJackson2HttpMessageConverter());
//	}

    // 当系统使用了MyResponseBodyAdvice后，而springmvc接口返回的是一个字符串，而不是对象Object类型，这个时候会报错。当开启这个功能的时候，springmvc接口可以返回字符串
//	@Override
//	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);
//	}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowCredentials(true)
                .allowedHeaders("*");
    }
}