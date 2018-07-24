package org.flow.boot.db.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.flow.boot.common.Response;
import org.flow.boot.common.util.GsonUtil;
import org.flow.boot.common.util.JavaHttpUtil;
import org.flow.boot.common.util.JavaRequest;
import org.flow.boot.common.util.JavaRequest.RequestMethod;
import org.flow.boot.db.dto.RpcResponse;
import org.flow.boot.db.dto.StepDTO;
import org.flow.boot.db.dto.StepPageDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("ticket")
public class TicketController implements FlowController {

    private static final String FLOW_HTML = "http://localhost:9090/m-workflow/process/step/query/html";
    private static final String FLOW_EXECUTE = "http://localhost:9090/m-workflow/process/step/command/execute";

    @Override
    public String stepPage(StepDTO step, HttpServletRequest request, HttpServletResponse response) {

        JavaRequest jr = new JavaRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Iterator<Entry<String, String[]>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, String[]> entry = iterator.next();
            jr.setParam(entry.getKey(), StringUtils.join(entry.getValue()));
        }

        String json = JavaHttpUtil.sendGet(FLOW_HTML, jr);
        RpcResponse resp = GsonUtil.fromJson(json, RpcResponse.class);
        return resp.getResult().toString();

    }

    @Override
    public Response<?> stepPageData(StepDTO step) {
        return Response.okResponse();
    }

    @Override
    public Response<?> stepConfirm(StepPageDTO stepPage, HttpServletRequest request) {
        Map<String, Object> pageParam = WebUtils.getParametersStartingWith(request, "page_");
        stepPage.setPageParam(pageParam);
        System.out.println(stepPage);

        JavaRequest jr = new JavaRequest();
        jr.setMethod(RequestMethod.PUT);
        Map<String, String> param = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Iterator<Entry<String, String[]>> iterator = entrySet.iterator(); iterator.hasNext();) {
            Entry<String, String[]> entry = iterator.next();
            param.put(entry.getKey(), StringUtils.join(entry.getValue()));
        }
        jr.setContent(GsonUtil.toJson(param));
        jr.setHeader("Content-Type", "application/json;charset-utf-8");

        String json = JavaHttpUtil.sendPut(FLOW_EXECUTE, jr);

        RpcResponse resp = GsonUtil.fromJson(json, RpcResponse.class);

        return Response.okResponse(resp.getResult());
    }

}
