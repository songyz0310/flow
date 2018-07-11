package org.flow.boot.process.service.test;

import java.util.List;

import org.flow.boot.common.vo.process.test.HistoricActivityInstanceVO;
import org.flow.boot.common.vo.process.test.ProcessInstanceVO;

public interface TestRuntimeService {

	ProcessInstanceVO startFlowInstanceById(String processDefinitionId);

	List<ProcessInstanceVO> queryInstanceList();

	List<HistoricActivityInstanceVO> queryActivityList(String processInstanceId);

}
