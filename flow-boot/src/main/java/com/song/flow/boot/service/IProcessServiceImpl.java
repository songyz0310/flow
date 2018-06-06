package com.song.flow.boot.service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.common.view.ProcessDefinitionView;

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

	public List<ProcessDefinitionView> queryById(String id) {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//
				.processDefinitionId(id)//
				.list();
		List<ProcessDefinitionView> data = new LinkedList<>();
		list.forEach(pd -> {
			ProcessDefinitionView vo = new ProcessDefinitionView();
			BeanUtils.copyProperties(pd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}

	public List<ProcessDefinitionView> queryList() {
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

	@Transactional
	public void clear() {
		repositoryService.createDeploymentQuery().list().forEach(pd -> repositoryService.deleteDeployment(pd.getId()));
	}
}
