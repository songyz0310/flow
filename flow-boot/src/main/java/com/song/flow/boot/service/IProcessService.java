package com.song.flow.boot.service;

import java.util.List;

import com.song.flow.boot.common.form.FileForm;
import com.song.flow.boot.common.view.ProcessDefinitionView;

public interface IProcessService {

	public void deploy(FileForm form);
	
	public List<ProcessDefinitionView> queryById(String id);

	public List<ProcessDefinitionView> queryList();

	public void clear();
	
}
