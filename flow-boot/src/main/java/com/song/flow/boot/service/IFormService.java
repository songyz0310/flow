package com.song.flow.boot.service;

import java.util.List;

import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.common.view.FormDefinitionView;

public interface IFormService {

	public void deploy(FileForm form);

	public FormDefinitionView queryById(String id);

	public List<FormDefinitionView> queryList();

	public void clear();

}
