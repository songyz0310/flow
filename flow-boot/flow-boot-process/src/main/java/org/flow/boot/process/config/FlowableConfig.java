package org.flow.boot.process.config;

import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableConfig {

	@Autowired
	private ProcessEngine processEngine;

	@Bean
	public RepositoryService getRepositoryService() {
		return processEngine.getRepositoryService();
	}

	@Bean
	public RuntimeService getRuntimeService() {
		return processEngine.getRuntimeService();
	}

	@Bean
	public FormService getFormService() {
		return processEngine.getFormService();
	}

	@Bean
	public TaskService getTaskService() {
		return processEngine.getTaskService();
	}

	@Bean
	public HistoryService getHistoryService() {
		return processEngine.getHistoryService();

	}

	@Bean
	public IdentityService getIdentityService() {
		return processEngine.getIdentityService();
	}

	@Bean
	public ManagementService getManagementService() {
		return processEngine.getManagementService();
	}

	@Bean
	public DynamicBpmnService getDynamicBpmnService() {
		return processEngine.getDynamicBpmnService();
	}

	// @Bean
	// public LocalContainerEntityManagerFactoryBean
	// getLocalContainerEntityManagerFactoryBean() {
	// return processEngine.getDynamicBpmnService();
	// }

}
