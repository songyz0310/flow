package org.flow.boot.process;
import org.flow.boot.process.repository.FlowInstanceRepository;
import org.flow.boot.process.repository.FlowProcessRepository;
import org.flow.boot.process.repository.FlowStepExtenseRepository;
import org.flow.boot.process.repository.FlowStepRepository;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.shared.Application;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {
    
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private FlowStepRepository flowStepRepository;
    @Autowired
    private FlowProcessRepository flowProcessRepository;
    @Autowired
    private FlowStepExtenseRepository flowStepExtenseRepository;
    @Autowired
    private FlowInstanceRepository flowInstanceRepository;

    @Test
    public void deploy()  {
        System.out.println(repositoryService);
    }
}
