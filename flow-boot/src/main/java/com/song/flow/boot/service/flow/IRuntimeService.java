package com.song.flow.boot.service.flow;

import com.song.flow.boot.model.view.flow.ProcessInstanceView;

public interface IRuntimeService {

	ProcessInstanceView startFlowInstanceById(String processDefinitionId);
}
