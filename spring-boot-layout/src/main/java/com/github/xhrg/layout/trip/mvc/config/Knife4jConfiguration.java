package com.github.xhrg.layout.trip.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

	@Bean
	public Docket dockerBean() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder().description("# demo接口").version("1.0").build()).groupName("demo").select()
				.apis(RequestHandlerSelectors.basePackage("com.github.xhrg")).paths(PathSelectors.any()).build();

//		docket.pathProvider(new DefaultPathProvider() {
//			public String getDocumentationPath() {
//				return "/docs";
//			}
//		});
		return docket;
	}
}