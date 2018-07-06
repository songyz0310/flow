package org.flow.boot.ticket.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flow.boot.common.Response;
import org.flow.boot.common.dto.ticket.StepActivityDTO;
import org.flow.boot.common.dto.ticket.StepDTO;
import org.flow.boot.common.dto.ticket.StepJumpDTO;
import org.flow.boot.common.dto.ticket.StepPageDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 流程相关
 * 
 * @author songyz
 *
 */
public interface FlowController {

	// 流程页面
	@GetMapping(value = "step/page", produces = MediaType.TEXT_HTML_VALUE)
	public String stepPage(StepDTO step, HttpServletRequest request, HttpServletResponse response);

	// 流程页面数据
	@GetMapping(value = "step/page/data")
	public Response<?> stepPageData(StepDTO step);

	// 流程页面确认
	@PostMapping(value = "step/confirm")
	public Response<?> stepConfirm(StepPageDTO stepPage, HttpServletRequest request);

	// 非页面流程，执行下一步
	@PostMapping(value = "step/execute")
	public Response<?> stepExecute(StepActivityDTO stepActivity);

	// 流程回退
	@PostMapping(value = "step/cancel")
	public Response<?> stepCancel(StepDTO step);

	// 流程快速完成
	@PostMapping(value = "step/jumpTo")
	public Response<?> stepJumpTo(StepJumpDTO step);

}
