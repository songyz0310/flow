package org.flow.boot.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer2 {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 使用JmsListener配置消费者监听的队列，其中text是接收到的消息
	@JmsListener(destination = "log.queue")
	public void receiveQueue(String text) {
		logger.info("发送日志:" + text);
	}

}
