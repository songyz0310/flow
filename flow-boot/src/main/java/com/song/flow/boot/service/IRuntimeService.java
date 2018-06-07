package com.song.flow.boot.service;

import com.song.flow.boot.common.view.ProcessInstanceView;

public interface IRuntimeService {

	ProcessInstanceView startFlowInstanceById(String processDefinitionId);
}
