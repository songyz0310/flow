package com.song.flow.boot.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.flow.boot.common.view.ProcessInstanceView;

@Service
public class IRuntimeServiceImpl implements IRuntimeService {

	@Autowired
	private RuntimeService runtimeService;

	@Transactional
	public ProcessInstanceView startFlowInstanceById(String processDefinitionId) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
		ProcessInstanceView vo = new ProcessInstanceView();
		BeanUtils.copyProperties(processInstance, vo);
		return vo;
	}

}
