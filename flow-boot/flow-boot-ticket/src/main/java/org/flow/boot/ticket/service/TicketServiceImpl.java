package org.flow.boot.ticket.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.flow.boot.common.dto.ticket.FlowStepDTO;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.FlowProcessVO;
import org.flow.boot.common.vo.process.FlowStepVO;
import org.flow.boot.common.vo.ticket.StepDataVO;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.entity.SysFlowStepData;
import org.flow.boot.ticket.entity.SysTicket;
import org.flow.boot.ticket.form.TicketForm;
import org.flow.boot.ticket.repository.SysFlowStepDataRepository;
import org.flow.boot.ticket.repository.SysTicketRepository;
import org.flow.boot.ticket.service.feignclient.FlowControllerService;
import org.flow.boot.ticket.service.feignclient.StepControllerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private SysTicketRepository sysTicketRepository;
	@Autowired
	private FlowControllerService flowControllerService;
	@Autowired
	private StepControllerService stepControllerService;
	@Autowired
	private SysFlowStepDataRepository sysFlowStepDataRepository;

	public List<TicketVO> ticketList() {
		List<TicketVO> data = new LinkedList<>();
		sysTicketRepository.findAll().forEach(ticket -> {
			TicketVO vo = new TicketVO();
			BeanUtils.copyProperties(ticket, vo);
			data.add(vo);
		});
		return data;
	}

	@Transactional
	public void openTicket(TicketForm form) {
		Date now = new Date();
		SysTicket ticket = new SysTicket();
		ticket.setCreateTime(now);
		ticket.setUpdateTime(now);
		BeanUtils.copyProperties(form, ticket);
		sysTicketRepository.save(ticket);

		String ticketId = ticket.getTicketId();
		String processId = form.getProcessId();
		FlowInstanceVO flowInstanceVO = flowControllerService.start(EntityType.TICKET, processId, ticketId).getData();
		FlowProcessVO flowProcessVO = flowControllerService.findById(processId).getData();
		FlowStepVO flowStepVO = stepControllerService.findById(flowInstanceVO.getStepId()).getData();
		ticket.setProcessName(flowProcessVO.getProcessName());
		ticket.setSoStatus("已开单");// TODO 从扩展中取值
		ticket.setStepId(flowStepVO.getStepId());
		ticket.setStepName(flowStepVO.getStepName());
		ticket.setStepType(flowStepVO.getStepType());
		sysTicketRepository.save(ticket);
	}

	public String getTicketFlowPage(String ticketId) {
		SysTicket ticket = sysTicketRepository.findOne(ticketId);
		String stepId = ticket.getStepId();
		FlowStepVO step = stepControllerService.findById(stepId).getData();
		String pageId = step.getPageId();

		File file = new File("target\\classes\\templates\\ticket\\" + pageId + ".html");
		if (file.exists() == false) {
			try {
				if (file.getParentFile().exists() == false)
					file.getParentFile().mkdirs();

				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String html = flowControllerService.getRenderedHtml(EntityType.TICKET, ticketId).getData();
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				writer.write(html);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pageId;

	}

	public List<StepDataVO> getTicketFlowData(EntityType entityType, String entityId, String stepId) {
		SysFlowStepData search = new SysFlowStepData();
		search.setEntityId(entityId);
		search.setEntityType(entityType);
		search.setStepId(stepId);
		Sort sort = new Sort(Sort.Direction.ASC, "dataId");
		List<SysFlowStepData> flStepDatas = sysFlowStepDataRepository.findAll(Example.of(search), sort);
		List<StepDataVO> dataList = new ArrayList<>();
		BeanUtils.copyProperties(flStepDatas, dataList);
		return dataList;
	}

	@Transactional
	public void flowExecute(FlowStepDTO flowStep) {
		
//		SysTicket ticket = sysTicketRepository.findOne(ticketId);
//		FlowInstanceVO flowInstanceVO = flowControllerService.completeStep(EntityType.TICKET, ticketId).getData();
//		FlowStepVO flowStepVO = stepControllerService.findById(flowInstanceVO.getStepId()).getData();
//		ticket.setSoStatus("---");// TODO 从扩展中取值
//		ticket.setStepId(flowStepVO.getStepId());
//		ticket.setStepName(flowStepVO.getStepName());
//		ticket.setStepType(flowStepVO.getStepType());
//		ticket.setUpdateTime(new Date());
//		sysTicketRepository.save(ticket);
	}

}
