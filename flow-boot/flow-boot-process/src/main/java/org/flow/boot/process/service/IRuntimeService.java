package org.flow.boot.process.service;

import org.flow.boot.process.view.ProcessInstanceView;

public interface IRuntimeService {

	ProcessInstanceView startFlowInstanceById(String processDefinitionId);
}
