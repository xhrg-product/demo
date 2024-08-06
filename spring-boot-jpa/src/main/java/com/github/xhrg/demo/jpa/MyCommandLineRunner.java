package com.github.xhrg.demo.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.github.xhrg.demo.jpa.dao.XxxRepository;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	private XxxRepository xxxRepository;

	@Override
	public void run(String... args) throws Exception {
		Pageable searchPage = PageRequest.of(0, 10, Sort.by("update_time").descending());
		xxxRepository.findByPage("name", searchPage);
	}

}
