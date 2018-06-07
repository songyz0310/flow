package com.song.flow.boot.service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.flow.boot.common.form.MyForm;
import com.song.flow.boot.common.view.FormDefinitionView;

@Service
public class IFormServiceImpl implements IFormService {

	private static final Logger logger = LoggerFactory.getLogger(IFormServiceImpl.class);

	@Autowired
	private FormRepositoryService formRepositoryService;

	@Transactional
	public void deploy(MyForm form) {
		switch (form.getType()) {
		case JSON:
			try (InputStream inputStream = form.getFile()) {
				formRepositoryService.createDeployment()//
						.parentDeploymentId(form.getParentDeploymentId())//
						.addInputStream(form.getKey(), inputStream)//
						.name(form.getName())//
						.deploy();
			} catch (Exception e) {
				logger.error("表单部署Json异常，exception:{}", e);
				throw new RuntimeException(e);
			}
			break;
		default:
			logger.error("流程部署文件格式有问题，type:{}", form.getType());
			break;
		}

	}

	public FormDefinitionView queryById(String formId) {
		FormDefinition fd = formRepositoryService.createFormDefinitionQuery().formId(formId).singleResult();
		if (Objects.isNull(fd)) {
			return null;
		}

		FormDefinitionView vo = new FormDefinitionView();
		BeanUtils.copyProperties(fd, vo);
		return vo;
	}

	public List<FormDefinitionView> queryList() {
		List<FormDefinition> list = formRepositoryService.createFormDefinitionQuery().list();
		List<FormDefinitionView> data = new LinkedList<>();
		list.forEach(fd -> {
			FormDefinitionView vo = new FormDefinitionView();
			BeanUtils.copyProperties(fd, vo);
			data.add(vo);
		});
		list.clear();
		return data;
	}

	@Transactional
	public void clear() {
		formRepositoryService.createFormDefinitionQuery().list()
				.forEach(fd -> formRepositoryService.deleteDeployment(fd.getDeploymentId()));
	}

}
