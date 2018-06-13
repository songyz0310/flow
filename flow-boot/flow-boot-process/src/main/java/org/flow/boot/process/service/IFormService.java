package org.flow.boot.process.service;

import java.util.List;

import org.flow.boot.process.form.MyForm;
import org.flow.boot.process.view.FormDefinitionView;

public interface IFormService {

	public void deploy(MyForm form);

	public FormDefinitionView queryById(String formId);

	public List<FormDefinitionView> queryList();

	public void clear();

}
