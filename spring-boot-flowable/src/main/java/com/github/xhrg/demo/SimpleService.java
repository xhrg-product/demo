package com.github.xhrg.demo;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleService {
	
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	public void startFlow() {
		runtimeService.startProcessInstanceByKey("");
	}

	public void findTask() {
		taskService.createTaskQuery().taskAssignee("zhangsan").list();
	}

	
	public void pass() {
		taskService.complete("taskId");
	}
	
	public void revert() {
		
	}
}
