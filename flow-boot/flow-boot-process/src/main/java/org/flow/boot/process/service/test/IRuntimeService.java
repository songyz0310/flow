package org.flow.boot.process.service.test;

import java.util.List;

import org.flow.boot.common.vo.process.HistoricActivityInstanceVO;
import org.flow.boot.common.vo.process.ProcessInstanceVO;

public interface IRuntimeService {

	ProcessInstanceVO startFlowInstanceById(String processDefinitionId);

	List<ProcessInstanceVO> queryInstanceList();

	List<HistoricActivityInstanceVO> queryActivityList(String processInstanceId);

}
