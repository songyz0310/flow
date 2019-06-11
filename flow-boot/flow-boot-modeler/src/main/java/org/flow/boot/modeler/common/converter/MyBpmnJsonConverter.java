package org.flow.boot.modeler.common.converter;

import org.flowable.editor.language.json.converter.BpmnJsonConverter;

public class MyBpmnJsonConverter extends BpmnJsonConverter {

    static {
        convertersToBpmnMap.put(STENCIL_TASK_USER, MyUserTaskJsonConverter.class);
    }
}
