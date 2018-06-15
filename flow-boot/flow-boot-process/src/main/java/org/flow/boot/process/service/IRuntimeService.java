package org.flow.boot.process.service;

import org.flow.boot.common.vo.process.ProcessInstanceView;

public interface IRuntimeService {

	ProcessInstanceView startFlowInstanceById(String processDefinitionId);
}
