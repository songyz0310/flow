package org.flow.boot.db;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	@Autowired
	private JmsMessagingTemplate jmsTemplate;

	// 发送消息，destination是发送到的队列，message是待发送的消息
	public void sendMessage(Destination destination, final Object message) {
		jmsTemplate.convertAndSend(destination, message);
	}

}
