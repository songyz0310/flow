package org.flow.boot.ticket.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.flow.boot.common.dto.ticket.StepActivityDTO;
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

		String ticketId = ticket.getTicketId();
		String processId = form.getProcessId();
		FlowInstanceVO flowInstanceVO = flowControllerService.start(EntityType.TICKET, processId, ticketId).getData();
		FlowProcessVO flowProcessVO = flowControllerService.queryById(processId).getData();
		FlowStepVO flowStepVO = stepControllerService.queryById(flowInstanceVO.getStepId()).getData();
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
	public void flowExecute(StepActivityDTO stepActivity) {
		Date now = new Date();

		String ticketId = stepActivity.getEntityId();
		String stepId = stepActivity.getStepId();

		SysTicket ticket = sysTicketRepository.findOne(ticketId);

		FlowStepVO stepVO = stepControllerService.queryById(stepId).getData();
		ticket.setSoStatus(stepVO.getStepName());// TODO 从当前步骤扩展中取值

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
			// TODO 待关单
			ticket.setSoStatus("待关单");
			ticket.setStepId(null);
			ticket.setStepName(null);
			ticket.setStepType(null);
		}
		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);

	}

	@Transactional
	public void flowConfirm(StepPageDTO stepPage) {
		Date now = new Date();

		String ticketId = stepPage.getEntityId();
		String stepId = stepPage.getStepId();

		SysTicket ticket = sysTicketRepository.findOne(ticketId);

		FlowStepVO stepVO = stepControllerService.queryById(stepId).getData();
		ticket.setSoStatus(stepVO.getStepName());// TODO 从当前步骤扩展中取值

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
				// flowStepData.setValueType(ValueType.);//从组件中配置获取
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
			// TODO 待关单
			ticket.setSoStatus("待关单");
			ticket.setStepId(null);
			ticket.setStepName(null);
			ticket.setStepType(null);
		}

		ticket.setUpdateTime(now);
		sysTicketRepository.save(ticket);

	}

}
