package org.flow.boot.process.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flow.boot.common.util.CommonUtil;
import org.flow.boot.process.entity.FlowPage;
import org.flow.boot.process.entity.FlowPageConfig;
import org.flow.boot.process.entity.FlowPageItem;
import org.flow.boot.process.repository.FlowPageConfigRepository;
import org.flow.boot.process.repository.FlowPageItemRepository;
import org.flow.boot.process.repository.FlowPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowPageServiceImpl implements FlowPageService {

	@Autowired
	private FlowPageRepository flowPageRepository;
	@Autowired
	private FlowPageItemRepository flowPageItemRepository;
	@Autowired
	private FlowPageConfigRepository flowPageConfigRepository;

	public void createPage(String pageId) {
		FlowPage flowPage = flowPageRepository.findOne(pageId);
		File file = new File("src\\main\\resources\\templates\\" + flowPage.getPageId() + ".html");
		if (file.exists()) {
			return;
		}
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		InputStream in = getClass().getClassLoader().getResourceAsStream("templates/template.html");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {

			StringBuilder pageItems = new StringBuilder();
			Map<String, String> map = new HashMap<>();

			StringBuilder pageTemp = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				pageTemp.append(line);
			}
			List<FlowPageConfig> pageConfigs = flowPageConfigRepository.findByPageId(pageId);
			for (FlowPageConfig fpc : pageConfigs) {
				FlowPageItem pageItem = flowPageItemRepository.findOne(fpc.getItemId());

				map.put("tableKey", "");
				map.put("tableVal", "");

				map.put("defaultValue", "");
				map.put("title", fpc.getName());
				map.put("required", "");
				map.put("reg", "");
				map.put("regTip", "");
				map.put("itemId", fpc.getItemId());
				map.put("fpcId", fpc.getConfigId());
				map.put("selectListVal", "");
				map.put("attachments", "");

				pageItems.append(CommonUtil.formString(pageItem.getHtml(), map));
			}

			map.put("pageName", flowPage.getPageName());
			map.put("source", pageItems.toString().replaceAll("\\$", "\\\\\\$"));
			String html = CommonUtil.formString(pageTemp.toString(), map);

			writer.write(html);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
