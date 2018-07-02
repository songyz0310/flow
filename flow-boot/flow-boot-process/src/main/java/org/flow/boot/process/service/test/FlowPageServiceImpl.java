package org.flow.boot.process.service.test;

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

import org.flow.boot.common.enums.EntityType;
import org.flow.boot.common.util.CommonUtil;
import org.flow.boot.process.entity.FlowPage;
import org.flow.boot.process.entity.FlowPageConfig;
import org.flow.boot.process.entity.FlowPageItem;
import org.flow.boot.process.repository.FlowPageConfigRepository;
import org.flow.boot.process.repository.FlowPageItemRepository;
import org.flow.boot.process.repository.FlowPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowPageServiceImpl implements FlowPageService {

	@Autowired
	private FlowPageRepository flowPageRepository;
	@Autowired
	private FlowPageItemRepository flowPageItemRepository;
	@Autowired
	private FlowPageConfigRepository flowPageConfigRepository;

	public void initPage() {

		flowPageRepository.findAll().forEach(flowPage -> {
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
					pageTemp.append(line).append("\r\n");
				}
				List<FlowPageConfig> pageConfigs = flowPageConfigRepository.findByPageId(flowPage.getPageId());
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
				e.printStackTrace();
			}
		});

	}

	@Transactional
	public void initData() {
		FlowPageItem flowPageItem = new FlowPageItem();
		flowPageItem.setName("单行文本录入");
		flowPageItem.setHtml("<div class=\"form-group\">\r\n" + //
				"	<label class=\"col-sm-2 control-label\">#{title}</label>\r\n" + //
				"	<div class=\"col-sm-10\">\r\n" + //
				"		<input type=\"text\" class=\"form-control\"\r\n" + //
				"			name=\"page_#{fpcId}_#{tableVal}\" placeholder=\"请填写#{title}\"\r\n" + //
				"			data-itemid=\"#{itemId}\" data-fpcid=\"#{fpcId}\"\r\n" + //
				"			data-required=\"#{required}\" data-reg=\"#{reg}\" data-regTip=\"#{regTip}\">\r\n" + //
				"	</div>\r\n" + //
				"</div>\r\n");//

		flowPageItemRepository.save(flowPageItem);

		FlowPage appointPage = new FlowPage();
		appointPage.setEntityType(EntityType.TICKET);
		appointPage.setPageName("预约");
		flowPageRepository.save(appointPage);

		FlowPage finishPage = new FlowPage();
		finishPage.setEntityType(EntityType.TICKET);
		finishPage.setPageName("完成");
		flowPageRepository.save(finishPage);
		
		FlowPageConfig fpc1 = new FlowPageConfig();
		fpc1.setItemId(flowPageItem.getItemId());
		fpc1.setPageId(appointPage.getPageId());
		fpc1.setName("工单客户");
		fpc1.setRank(0);
		flowPageConfigRepository.save(fpc1);
		
		FlowPageConfig fpc2 = new FlowPageConfig();
		fpc2.setItemId(flowPageItem.getItemId());
		fpc2.setPageId(appointPage.getPageId());
		fpc2.setName("工单联系人");
		fpc2.setRank(1);
		flowPageConfigRepository.save(fpc2);

		FlowPageConfig fpc3 = new FlowPageConfig();
		fpc3.setItemId(flowPageItem.getItemId());
		fpc3.setPageId(appointPage.getPageId());
		fpc3.setName("联系电话");
		fpc3.setRank(2);
		flowPageConfigRepository.save(fpc3);

		FlowPageConfig fpc4 = new FlowPageConfig();
		fpc4.setItemId(flowPageItem.getItemId());
		fpc4.setPageId(appointPage.getPageId());
		fpc4.setName("上门时间");
		fpc4.setRank(3);
		flowPageConfigRepository.save(fpc4);

		FlowPageConfig fpc5 = new FlowPageConfig();
		fpc5.setItemId(flowPageItem.getItemId());
		fpc5.setPageId(appointPage.getPageId());
		fpc5.setName("上门地址");
		fpc5.setRank(4);
		flowPageConfigRepository.save(fpc5);

		FlowPageConfig fpc6 = new FlowPageConfig();
		fpc6.setItemId(flowPageItem.getItemId());
		fpc6.setPageId(finishPage.getPageId());
		fpc6.setName("最终联系人");
		fpc6.setRank(0);
		flowPageConfigRepository.save(fpc6);

		FlowPageConfig fpc7 = new FlowPageConfig();
		fpc7.setItemId(flowPageItem.getItemId());
		fpc7.setPageId(finishPage.getPageId());
		fpc7.setName("联系电话");
		fpc7.setRank(1);
		flowPageConfigRepository.save(fpc7);

		FlowPageConfig fpc8 = new FlowPageConfig();
		fpc8.setItemId(flowPageItem.getItemId());
		fpc8.setPageId(finishPage.getPageId());
		fpc8.setName("故障描述");
		fpc8.setRank(2);
		flowPageConfigRepository.save(fpc8);

		FlowPageConfig fpc9 = new FlowPageConfig();
		fpc9.setItemId(flowPageItem.getItemId());
		fpc9.setPageId(finishPage.getPageId());
		fpc9.setName("现场确认");
		fpc9.setRank(3);
		flowPageConfigRepository.save(fpc9);

		FlowPageConfig fpc10 = new FlowPageConfig();
		fpc10.setItemId(flowPageItem.getItemId());
		fpc10.setPageId(finishPage.getPageId());
		fpc10.setName("解决方案");
		fpc10.setRank(4);
		flowPageConfigRepository.save(fpc10);

	}

}
