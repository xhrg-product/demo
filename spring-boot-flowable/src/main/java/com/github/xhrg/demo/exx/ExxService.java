package com.github.xhrg.demo.exx;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

public class ExxService {

	private static final String TENANT_ID = "t1";

	@Autowired
	private RuntimeService runtimeService;

	public void startProcessInstance() {

		String processDefinitionKey = "demo-process";
		String processInstanceName = "测试流程";
		String businessKey = UUID.randomUUID().toString();

		Map<String, Object> params = new HashMap<>();

		ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
		processInstanceBuilder.processDefinitionKey(processDefinitionKey).businessKey(businessKey)
				.name(processInstanceName).variables(params).tenantId(TENANT_ID);
		ProcessInstance processInstance = processInstanceBuilder.start();
		System.out.println(processInstance.getId());
	}

	public void close() {
		String procInstId = "";
		runtimeService.deleteProcessInstance(procInstId, "canals");
	}
}
