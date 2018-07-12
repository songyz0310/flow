package org.flow.boot.db;

import javax.jms.Destination;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Producer producer;

	@Test
	public void sendMessage() {

		Destination message = new ActiveMQQueue("message.queue");
		Destination log = new ActiveMQQueue("log.queue");

		producer.sendMessage(message, "生产者发送了消息");
		producer.sendMessage(log, "生产者发送了日志");

	}
}
