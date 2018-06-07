package com.song.flow.boot.service;

import java.util.List;

import com.song.flow.boot.common.form.MyForm;
import com.song.flow.boot.common.view.FormDefinitionView;

public interface IFormService {

	public void deploy(MyForm form);

	public FormDefinitionView queryById(String formId);

	public List<FormDefinitionView> queryList();

	public void clear();

}
