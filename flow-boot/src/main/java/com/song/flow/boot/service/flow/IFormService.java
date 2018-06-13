package com.song.flow.boot.service.flow;

import java.util.List;

import com.song.flow.boot.model.form.flow.MyForm;
import com.song.flow.boot.model.view.flow.FormDefinitionView;

public interface IFormService {

	public void deploy(MyForm form);

	public FormDefinitionView queryById(String formId);

	public List<FormDefinitionView> queryList();

	public void clear();

}
