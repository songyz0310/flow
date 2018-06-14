package org.flow.boot.process.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipInputStream;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.enums.StepType;
import org.flow.boot.process.entity.FlowProcess;
import org.flow.boot.process.entity.FlowStep;
import org.flow.boot.process.form.FileForm;
import org.flow.boot.process.form.ProcessForm;
import org.flow.boot.process.repository.FlowProcessRepository;
import org.flow.boot.process.repository.FlowStepRepository;
import org.flow.boot.process.view.DeploymentView;
import org.flow.boot.process.view.ProcessDefinitionView;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CustomProperty;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.common.impl.util.io.InputStreamSource;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IProcessServiceImpl implements IProcessService {

	private static final Logger logger = LoggerFactory.getLogger(IProcessServiceImpl.class);

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private FlowStepRepository flowStepRepository;
	@Autowired
	private FlowProcessRepository flowProcessRepository;

	@Transactional
	public void deploy(FileForm form) {
		switch (form.getType()) {
		case ZIP:
			try (ZipInputStream inputStream = new ZipInputStream(form.getFile())) {
				repositoryService.createDeployment()//
						.name(form.getName())//
						.addZipInputStream(inputStream)//
						.deploy();
			} catch (Exception e) {
				logger.error("流程部署Zip异常，exception:{}", e);
				throw new RuntimeException(e);
			}
			break;
		case XML:
			try (InputStream inputStream = form.getFile()) {
				repositoryService.createDeployment()//
						.name(form.getName())//
						.addInputStream(form.getKey(), inputStream)//
						.deploy();
			} catch (Exception e) {
				logger.error("流程部署Xml异常，exception:{}", e);
				throw new RuntimeException(e);
			}
			break;
		default:
			logger.error("流程部署文件格式有问题，type:{}", form.getType());
			break;
		}
	}

	public List<ProcessDefinitionView> queryDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
		List<ProcessDefinitionView> data = new LinkedList<>();
		list.forEach(pd -> {
			ProcessDefinitionView vo = new ProcessDefinitionView();
			BeanUtils.copyProperties(pd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}

	public DeploymentView queryDeploymentById(String deploymentId) {
		Deployment deployment = repositoryService.createDeploymentQuery()//
				.deploymentId(deploymentId)//
				.singleResult();
		DeploymentView vo = new DeploymentView();
		BeanUtils.copyProperties(deployment, vo);
		return vo;
	}

	public List<DeploymentView> queryDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery().list();
		List<DeploymentView> data = new LinkedList<>();
		list.forEach(pd -> {
			DeploymentView vo = new DeploymentView();
			BeanUtils.copyProperties(pd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}

	@Transactional
	public void clear() {
		repositoryService.createDeploymentQuery().list().forEach(pd -> repositoryService.deleteDeployment(pd.getId()));
	}

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId) {

		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
				.singleResult();

		ProcessDefinitionView vo = new ProcessDefinitionView();
		BeanUtils.copyProperties(pd, vo);
		return vo;
	}

	@Transactional
	public void deploy(ProcessForm form) {
		try (ZipInputStream inputStream = new ZipInputStream(form.getFile())) {
			Date now = new Date();

			Deployment deployment = repositoryService.createDeployment()//
					.name(form.getName()).addZipInputStream(inputStream)//
					.deploy();

			ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()//
					.deploymentId(deployment.getId()).singleResult();

			FlowProcess flowProcess = new FlowProcess();
			flowProcess.setProcessId(pd.getId());
			flowProcess.setCreateTime(now);
			flowProcess.setEntityType(EntityType.TICKET);
			flowProcess.setFilePath("");
			flowProcess.setProcessKey(form.getKey());
			flowProcess.setProcessName(form.getName());
			flowProcess.setUpdateTime(now);
			flowProcessRepository.save(flowProcess);

			InputStream xmlIs = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());
			BpmnModel bm = new BpmnXMLConverter().convertToBpmnModel(new InputStreamSource(xmlIs), false, true);

			int stepRank = 0;
			Process process = bm.getProcesses().get(0);
			Collection<FlowElement> flowElements = process.getFlowElements();
			for (FlowElement flowElement : flowElements) {
				if (flowElement instanceof UserTask) {
					UserTask u = (UserTask) flowElement;
					FlowStep flowStep = new FlowStep();
					flowStep.setProcessId(pd.getId());
					flowStep.setStepName(u.getName());
					flowStep.setStepRank(stepRank);

					String formKey = u.getFormKey();
					if (Objects.equals(formKey, StepType.SIGNIN.name())) {
						flowStep.setStepType(StepType.SIGNIN);
					} else if (StringUtils.isNotEmpty(formKey)) {
						flowStep.setPageId(formKey.replace(".html", ""));
						flowStep.setStepType(StepType.PAGE);
					} else {
						flowStep.setStepType(StepType.EXECUTE);
					}
					
					List<CustomProperty> customProperties = u.getCustomProperties();
					for (CustomProperty customProperty : customProperties) {
						customProperty.getName();
						customProperty.getSimpleValue();
						//保存步骤扩展信息
					}
					
					flowStepRepository.save(flowStep);
					stepRank++;
				}
			}
		} catch (Exception e) {
			logger.error("流程部署Zip异常，exception:{}", e);
			throw new RuntimeException(e);
		}

	}

}
