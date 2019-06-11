package org.flow.boot.modeler.service.flowable;

import org.flow.boot.modeler.common.converter.MyBpmnJsonConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.ui.modeler.service.ModelServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * 扩展转换类
 * 
 * @author songyz<br>
 *         CreateTime: 2018-09-30 14:32
 */
@Service
@Primary
public class MyModelServiceImpl extends ModelServiceImpl implements MyModelService {

    protected BpmnJsonConverter bpmnJsonConverter = new MyBpmnJsonConverter();
    
}
