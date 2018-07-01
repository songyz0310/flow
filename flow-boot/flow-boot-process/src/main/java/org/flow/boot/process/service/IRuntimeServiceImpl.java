package org.flow.boot.process.service;

import java.util.LinkedList;
import java.util.List;

import org.flow.boot.common.vo.process.HistoricActivityInstanceVO;
import org.flow.boot.common.vo.process.ProcessInstanceVO;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.common.api.query.QueryProperty;
import org.flowable.engine.impl.HistoricActivityInstanceQueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IRuntimeServiceImpl implements IRuntimeService {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;

	@Transactional
	public ProcessInstanceVO startFlowInstanceById(String processDefinitionId) {
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);
		ProcessInstanceVO vo = new ProcessInstanceVO();
		BeanUtils.copyProperties(processInstance, vo);
		return vo;
	}

	public List<ProcessInstanceVO> queryInstanceList() {

		// runtimeService.createChangeActivityStateBuilder().processInstanceId(processInstanceId)
		// .cancelActivityId(currentNode).startActivityId(beforeNode).changeState();

		List<ProcessInstanceVO> list = new LinkedList<>();
		runtimeService.createProcessInstanceQuery().list().forEach(pi -> {
			ProcessInstanceVO vo = new ProcessInstanceVO();
			BeanUtils.copyProperties(pi, vo);
			list.add(vo);
		});
		return list;
	}

	public List<HistoricActivityInstanceVO> queryActivityList(String processInstanceId) {
		List<HistoricActivityInstanceVO> list = new LinkedList<>();
		historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).orderBy(HistoricActivityInstanceQueryProperty.START).asc().list().forEach(pi -> {
			HistoricActivityInstanceVO vo = new HistoricActivityInstanceVO();
			BeanUtils.copyProperties(pi, vo);
			list.add(vo);
		});
		return list;
	}

}
