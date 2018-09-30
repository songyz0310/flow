package org.flow.boot.modeler.utils;

import static org.flow.boot.modeler.common.constants.MyStencilConstants.NAMESPACE;
import static org.flow.boot.modeler.common.constants.MyStencilConstants.NAMESPACE_PREFIX;

import org.flowable.bpmn.model.ExtensionAttribute;

/**
 * 生成ExtensionAttribute 工具类
 * 
 * @author songyz<br>
 *         CreateTime: 2018-09-30 15:29
 */
public class ExtensionAttributeUtils {

    public static ExtensionAttribute generate(String key, String value) {
        ExtensionAttribute ea = new ExtensionAttribute();
        ea.setName(key);
        ea.setValue(value);
        ea.setNamespace(NAMESPACE);
        ea.setNamespacePrefix(NAMESPACE_PREFIX);
        return ea;
    }
}
