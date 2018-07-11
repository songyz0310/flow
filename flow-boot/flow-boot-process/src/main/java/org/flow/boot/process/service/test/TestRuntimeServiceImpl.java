package org.flow.boot.process.service.test;

import java.util.LinkedList;
import java.util.List;

import org.flow.boot.common.vo.process.test.HistoricActivityInstanceVO;
import org.flow.boot.common.vo.process.test.ProcessInstanceVO;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.HistoricActivityInstanceQueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestRuntimeServiceImpl implements TestRuntimeService {

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

		
		
		List<HistoricActivityInstance> list4 = historyService.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId).orderBy(HistoricActivityInstanceQueryProperty.START).asc().list();

		System.out.println(list4);
		System.out.println(list4.size());

		list4.forEach(pi -> {
			HistoricActivityInstanceVO vo = new HistoricActivityInstanceVO();
			BeanUtils.copyProperties(pi, vo);
			list.add(vo);
		});

		return list;
	}

}
