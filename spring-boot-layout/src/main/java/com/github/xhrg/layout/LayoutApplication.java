package com.github.xhrg.layout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LayoutApplication {

	private static Logger log = LoggerFactory.getLogger(LayoutApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LayoutApplication.class, args);
		log.info("started");
	}
}
