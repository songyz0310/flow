package org.flow.boot.ticket.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.flow.boot.common.dto.ticket.StepActivityDTO;
import org.flow.boot.common.dto.ticket.StepDTO;
import org.flow.boot.common.dto.ticket.StepJumpDTO;
import org.flow.boot.common.dto.ticket.StepPageDTO;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.vo.process.FlowInstanceVO;
import org.flow.boot.common.vo.process.FlowInstanceVO.Status;
import org.flow.boot.common.vo.process.FlowPageConfigVO;
import org.flow.boot.common.vo.process.FlowProcessVO;
import org.flow.boot.common.vo.process.FlowStepVO;
import org.flow.boot.common.vo.ticket.StepDataVO;
import org.flow.boot.common.vo.ticket.TicketVO;
import org.flow.boot.ticket.entity.SysFlowStepActivity;
import org.flow.boot.ticket.entity.SysFlowStepData;
import org.flow.boot.ticket.entity.SysTicket;
import org.flow.boot.ticket.form.TicketForm;
import org.flow.boot.ticket.repository.SysFlowStepActivityRepository;
import org.flow.boot.ticket.repository.SysFlowStepDataRepository;
import org.flow.boot.ticket.repository.SysTicketRepository;
import org.flow.boot.ticket.service.feignclient.FlowControllerService;
import org.flow.boot.ticket.service.feignclient.FlowPageControllerService;
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
	@Autowired
	private FlowPageControllerService flowPageControllerService;
	@Autowired
	private SysFlowStepActivityRepository sysFlowStepActivityRepository;

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

		HashMap<String, Object> variables = new HashMap<>();
		variables.put("ticket", ticket);

		FlowInstanceVO flowInstance = flowControllerService
				.start(EntityType.TICKET, form.getProcessId(), ticket.getTicketId(), variables).getData();
		FlowProcessVO flowProcessVO = flowControllerService.queryById(form.getProcessId()).getData();
		FlowStepVO flowStepVO = stepControllerService.queryById(flowInstance.getStepId()).getData();
		ticket.setProcessName(flowProcessVO.getProcessName());
		ticket.setStepId(flowStepVO.getStepId());
		ticket.setStepName(flowStepVO.getStepName());
		ticket.setStepType(flowStepVO.getStepType());
		sysTicketRepository.save(ticket);
	}

	public String getTicketFlowPage(String ticketId) {
		SysTicket ticket = sysTicketRepository.getOne(ticketId);
		String stepId = ticket.getStepId();
		FlowStepVO step = stepControllerService.queryById(stepId).getData();
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

	public List<StepDataVO> getTicketStepData(StepDTO dto) {
		SysFlowStepData search = new SysFlowStepData();
		search.setEntityId(dto.getEntityId());
		search.setEntityType(dto.getEntityType());
		search.setStepId(dto.getStepId());
		Sort sort = new Sort(Sort.Direction.ASC, "dataId");
		List<StepDataVO> dataList = new ArrayList<>();
		sysFlowStepDataRepository.findAll(Example.of(search), sort).forEach(fsd -> {
			StepDataVO vo = new StepDataVO();
			BeanUtils.copyProperties(fsd, vo);
			dataList.add(vo);
		});
		return dataList;
	}

	@Transactional
	public void stepExecute(StepActivityDTO stepActivity) {
		Date now = new Date();

		String ticketId = stepActivity.getEntityId();
		String stepId = stepActivity.getStepId();

		SysTicket ticket = sysTicketRepository.getOne(ticketId);

		FlowStepVO stepVO = stepControllerService.queryById(stepId).getData();

		SysFlowStepActivity activity = new SysFlowStepActivity();
		activity.setAddress(stepActivity.getAddress());
		activity.setCreateTime(now);
		activity.setEntityId(ticketId);
		activity.setEntityType(stepActivity.getEntityType());
		activity.setLatitude(stepActivity.getLatitude());
		activity.setLongitude(stepActivity.getLongitude());
		activity.setStepId(stepId);
		activity.setStepName(stepVO.getStepName());
		sysFlowStepActivityRepository.save(activity);

		FlowInstanceVO flowInstance = flowControllerService.completeStep(EntityType.TICKET, ticketId).getData();
		if (flowInstance.getStatus() == Status.RUNNING) {
			FlowStepVO nextStep = stepControllerService.queryById(flowInstance.getStepId()).getData();
			ticket.setStepId(nextStep.getStepId());
			ticket.setStepName(nextStep.getStepName());
			ticket.setStepType(nextStep.getStepType());
		} else if (flowInstance.getStatus() == Status.STOPED) {
			ticket.setStepId(null);
			ticket.setStepName(null);
			ticket.setStepType(null);
		}
		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);

	}

	@Transactional
	public void stepConfirm(StepPageDTO stepPage) {
		Date now = new Date();

		String ticketId = stepPage.getEntityId();
		String stepId = stepPage.getStepId();

		SysTicket ticket = sysTicketRepository.getOne(ticketId);

		FlowStepVO stepVO = stepControllerService.queryById(stepId).getData();

		SysFlowStepActivity activity = new SysFlowStepActivity();
		activity.setAddress(stepPage.getAddress());
		activity.setCreateTime(now);
		activity.setEntityId(ticketId);
		activity.setEntityType(stepPage.getEntityType());
		activity.setLatitude(stepPage.getLatitude());
		activity.setLongitude(stepPage.getLongitude());
		activity.setStepId(stepId);
		activity.setStepName(stepVO.getStepName());
		sysFlowStepActivityRepository.save(activity);

		if (Objects.nonNull(stepPage.getPageParam())) {
			stepPage.getPageParam().entrySet().forEach(entry -> {
				String key = entry.getKey();
				Object value = entry.getValue();
				String[] split = key.split("_");
				String fpcId = split[0];
				FlowPageConfigVO config = flowPageControllerService.queryById(fpcId).getData();
				SysFlowStepData flowStepData = new SysFlowStepData();
				flowStepData.setCreateTime(now);
				flowStepData.setEntityId(stepPage.getEntityId());
				flowStepData.setEntityType(stepPage.getEntityType());
				flowStepData.setFpcId(fpcId);
				flowStepData.setFpcName(config.getName());
				flowStepData.setStepId(stepId);
				flowStepData.setStepName(stepVO.getStepName());
				flowStepData.setValue(value.toString());
				sysFlowStepDataRepository.save(flowStepData);
			});
		}

		FlowInstanceVO flowInstance = flowControllerService.completeStep(EntityType.TICKET, ticketId).getData();
		if (flowInstance.getStatus() == Status.RUNNING) {
			FlowStepVO nextStep = stepControllerService.queryById(flowInstance.getStepId()).getData();
			ticket.setStepId(nextStep.getStepId());
			ticket.setStepName(nextStep.getStepName());
			ticket.setStepType(nextStep.getStepType());
		} else if (flowInstance.getStatus() == Status.STOPED) {
			ticket.setStepId(null);
			ticket.setStepName(null);
			ticket.setStepType(null);
		}
		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);

	}

	@Transactional
	public void stepCancel(StepDTO dto) {
		Date now = new Date();
		String ticketId = dto.getEntityId();
		SysTicket ticket = sysTicketRepository.getOne(ticketId);
		FlowInstanceVO flowInstance = flowControllerService.cancelStep(EntityType.TICKET, ticketId).getData();
		FlowStepVO step = stepControllerService.queryById(flowInstance.getStepId()).getData();
		ticket.setStepId(step.getStepId());
		ticket.setStepName(step.getStepName());
		ticket.setStepType(step.getStepType());
		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);
		sysFlowStepDataRepository.deleteByStepId(dto.getStepId());
	}

	@Transactional
	public void stepJumpTo(StepJumpDTO dto) {
		Date now = new Date();
		SysTicket ticket = sysTicketRepository.getOne(dto.getEntityId());
		FlowInstanceVO flowInstance = flowControllerService
				.jumpStepTo(EntityType.TICKET, dto.getEntityId(), dto.getJumpStepId()).getData();
		FlowStepVO step = stepControllerService.queryById(flowInstance.getStepId()).getData();
		ticket.setStepId(step.getStepId());
		ticket.setStepName(step.getStepName());
		ticket.setStepType(step.getStepType());
		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);
	}

}
