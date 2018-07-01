package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.common.vo.process.FormDefinitionVO;
import org.flow.boot.process.form.MyForm;

public interface IFormService {

	public void deploy(MyForm form);

	public FormDefinitionVO queryById(String formId);

	public List<FormDefinitionVO> queryList();

	public void clear();

}
