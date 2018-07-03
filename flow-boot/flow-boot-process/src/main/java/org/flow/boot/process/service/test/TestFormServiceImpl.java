package org.flow.boot.process.service.test;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.flow.boot.common.vo.process.FormDefinitionVO;
import org.flow.boot.process.form.MyForm;
import org.flowable.form.api.FormDefinition;
import org.flowable.form.api.FormRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestFormServiceImpl implements TestFormService {

	private static final Logger logger = LoggerFactory.getLogger(TestFormServiceImpl.class);

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

	public FormDefinitionVO queryById(String formId) {
		FormDefinition fd = formRepositoryService.createFormDefinitionQuery().formId(formId).singleResult();
		if (Objects.isNull(fd)) {
			return null;
		}

		FormDefinitionVO vo = new FormDefinitionVO();
		BeanUtils.copyProperties(fd, vo);
		return vo;
	}

	public List<FormDefinitionVO> queryList() {
		List<FormDefinition> list = formRepositoryService.createFormDefinitionQuery().list();
		List<FormDefinitionVO> data = new LinkedList<>();
		list.forEach(fd -> {
			FormDefinitionVO vo = new FormDefinitionVO();
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
