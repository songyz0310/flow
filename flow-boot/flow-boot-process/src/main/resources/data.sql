
-- ----------------------------
-- Records of flow_page
-- ----------------------------
truncate flow_page;
INSERT INTO `flow_page` VALUES ('40280981646485f9016464878d3a0001', 'TICKET', '预约');
INSERT INTO `flow_page` VALUES ('40280981646485f9016464878d3a0002', 'TICKET', '完成');


-- ----------------------------
-- Records of flow_page_config
-- ----------------------------
truncate flow_page_config;
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3d0003', '40280981646485f9016464878d280000', '工单客户', '40280981646485f9016464878d3a0001', 0);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3d0004', '40280981646485f9016464878d280000', '工单联系人', '40280981646485f9016464878d3a0001', 1);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3d0005', '40280981646485f9016464878d280000', '联系电话', '40280981646485f9016464878d3a0001', 2);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3d0006', '40280981646485f9016464878d280000', '上门时间', '40280981646485f9016464878d3a0001', 3);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e0007', '40280981646485f9016464878d280000', '上门地址', '40280981646485f9016464878d3a0001', 4);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e0008', '40280981646485f9016464878d280000', '最终联系人', '40280981646485f9016464878d3a0002', 0);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e0009', '40280981646485f9016464878d280000', '联系电话', '40280981646485f9016464878d3a0002', 1);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e000a', '40280981646485f9016464878d280000', '故障描述', '40280981646485f9016464878d3a0002', 2);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e000b', '40280981646485f9016464878d280000', '现场确认', '40280981646485f9016464878d3a0002', 3);
INSERT INTO `flow_page_config` VALUES ('40280981646485f9016464878d3e000c', '40280981646485f9016464878d280000', '解决方案', '40280981646485f9016464878d3a0002', 4);

-- ----------------------------
-- Records of flow_page_item
-- ----------------------------
truncate flow_page_item;
INSERT INTO `flow_page_item` VALUES ('40280981646485f9016464878d280000', '<div class=\"form-group\">\r\n	<label class=\"col-sm-2 control-label\">#{title}</label>\r\n	<div class=\"col-sm-10\">\r\n		<input type=\"text\" class=\"form-control\"\r\n			name=\"page_#{fpcId}_#{tableVal}\" placeholder=\"请填写#{title}\"\r\n			data-itemid=\"#{itemId}\" data-fpcid=\"#{fpcId}\"\r\n			data-required=\"#{required}\" data-reg=\"#{reg}\" data-regTip=\"#{regTip}\">\r\n	</div>\r\n</div>\r\n', '单行文本录入');

