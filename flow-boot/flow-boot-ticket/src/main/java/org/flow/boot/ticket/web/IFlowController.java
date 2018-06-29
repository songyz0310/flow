package org.flow.boot.ticket.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.flow.boot.common.Response;
import org.flow.boot.common.enums.EntityType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 流程相关
 * 
 * @author songyz
 *
 */
public interface IFlowController {

	/**
	 * 流程页面
	 * 
	 * @param entityId
	 * @return
	 */
	@GetMapping(value = "/flow/page", produces = MediaType.TEXT_HTML_VALUE)
	public String flowPage(String entityId, String stepId, HttpServletRequest req, HttpServletResponse resp);

	/**
	 * 流程页面数据
	 * 
	 * @param entityId
	 * @return
	 */
	@GetMapping(value = "/flow/page/data")
	public Response<?> flowPageData(EntityType entityType, String entityId, String stepId);

	/**
	 * 流程页面确认
	 * 
	 * @param entityId
	 * @return
	 */
	@PostMapping(value = "/flow/confirm")
	public Response<?> flowConfirm(HttpServletRequest request);

	/**
	 * 非页面流程，执行下一步
	 * 
	 * @param entityId
	 * @return
	 */
	@PostMapping(value = "flow/complete")
	public Response<?> flowComplete(String entityId);

}
