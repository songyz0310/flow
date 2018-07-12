package org.flow.boot.common.util;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

public class JavaRequest {

	private Map<String, String> header = new HashMap<String, String>();
	private Map<String, String> param = new HashMap<String, String>();

	// 设置网络代理
	private Proxy proxy;

	private Integer connectTimeout;
	private Integer readTimeout;

	// 如果param为空，则会把content写过去
	private String content = "";
	private RequestMethod method = RequestMethod.GET;

	public static enum RequestMethod {
		GET, POST, PUT
	}

	public void setHeader(String key, String value) {
		header.put(key, value);
	}

	public void setParam(String key, String value) {
		param.put(key, value);
	}

	public void setParams(Map<String, String> map) {
		param.putAll(map);
	}

	public Map<String, String> getHeaderMap() {
		return header;
	}

	public Map<String, String> getParamMap() {
		return param;
	}

	public void setRequestMethod(RequestMethod method) {
		this.method = method;
	}

	public RequestMethod getRequestMethod() {
		return method;
	}

	// 形如：name=xxx&age=xx
	public String getParamStr() {
		if (param.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String key : param.keySet()) {
			sb.append(key).append("=").append(param.get(key)).append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(String ip, int port) {
		this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

}