package com.song.flow.boot.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.FormProperty;
import org.flowable.bpmn.model.SequenceFlow;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.common.impl.util.io.InputStreamSource;
import org.flowable.engine.impl.RepositoryServiceImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.common.view.DeploymentView;
import com.song.flow.boot.common.view.ProcessDefinitionView;
import com.song.flow.boot.util.JacksonUtil;

@Service
public class IProcessServiceImpl implements IProcessService {

	private static final Logger logger = LoggerFactory.getLogger(IProcessServiceImpl.class);

	@Autowired
	private RepositoryService repositoryService;

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

	public ProcessDefinitionView queryDefinitionById(String processDefinitionId) {

		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId)
				.singleResult();
		InputStream xmlIs = repositoryService.getResourceAsStream(pd.getDeploymentId(), pd.getResourceName());
		BpmnModel bm = new BpmnXMLConverter().convertToBpmnModel(new InputStreamSource(xmlIs), false, true);

		Process process = bm.getProcesses().get(0);
		Collection<FlowElement> flowElements = process.getFlowElements();
		for (FlowElement flowElement : flowElements) {
			if (flowElement instanceof UserTask) {
				UserTask u = (UserTask) flowElement;
				System.err.println(JacksonUtil.getJson(u));
			}
		}

		ProcessDefinitionView vo = new ProcessDefinitionView();
		BeanUtils.copyProperties(pd, vo);
		return vo;
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

}
