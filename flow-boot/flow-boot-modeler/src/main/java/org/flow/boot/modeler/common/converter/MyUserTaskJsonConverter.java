package org.flow.boot.modeler.common.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.flow.boot.modeler.common.constants.MyStencilConstants.*;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.modeler.utils.ExtensionAttributeUtils;
import org.flowable.bpmn.model.BaseElement;
import org.flowable.bpmn.model.ExtensionAttribute;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.editor.language.json.converter.BaseBpmnJsonConverter;
import org.flowable.editor.language.json.converter.UserTaskJsonConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MyUserTaskJsonConverter extends UserTaskJsonConverter {

    public static void fillBpmnTypes(
            Map<Class<? extends BaseElement>, Class<? extends BaseBpmnJsonConverter>> convertersToJsonMap) {
        convertersToJsonMap.put(UserTask.class, MyUserTaskJsonConverter.class);
    }

    protected void convertElementToJson(ObjectNode propertiesNode, BaseElement baseElement) {
        super.convertElementToJson(propertiesNode, baseElement);
    }

    protected FlowElement convertJsonToElement(JsonNode elementNode, JsonNode modelNode,
            Map<String, JsonNode> shapeMap) {
        FlowElement flowElement = super.convertJsonToElement(elementNode, modelNode, shapeMap);
        if (flowElement instanceof UserTask) {
            UserTask userTask = (UserTask) flowElement;
            
            Map<String, List<ExtensionAttribute>> attributes = //
                    Objects.isNull(userTask.getAttributes()) ? new HashMap<>() : userTask.getAttributes();

            // 步骤提示
            String stepTip = getPropertyValueAsString(STEP_TIP, elementNode);
            if (StringUtils.isNotEmpty(stepTip)) {
                ExtensionAttribute stepTipEA = ExtensionAttributeUtils.generate(STEP_TIP, stepTip);
                attributes.put(STEP_TIP, Arrays.asList(stepTipEA));
            }

            // 步骤图标
            String stepIcon = getPropertyValueAsString(STEP_ICON, elementNode);
            if (StringUtils.isNotEmpty(stepIcon)) {
                ExtensionAttribute stepIconEA = ExtensionAttributeUtils.generate(STEP_ICON, stepIcon);
                attributes.put(STEP_ICON, Arrays.asList(stepIconEA));
            }

            // 步骤图标路径
            String stepIconPath = getPropertyValueAsString(STEP_ICON_PATH, elementNode);
            if (StringUtils.isNotEmpty(stepIconPath)) {
                ExtensionAttribute stepIconPathEA = ExtensionAttributeUtils.generate(STEP_ICON_PATH, stepIconPath);
                attributes.put(STEP_ICON_PATH, Arrays.asList(stepIconPathEA));
            }

            userTask.setAttributes(attributes);
        }
        return flowElement;
    }
}
