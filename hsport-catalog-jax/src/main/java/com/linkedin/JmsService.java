package com.linkedin;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

@ApplicationScoped
public class JmsService {
	private static final Logger LOG = Logger.getLogger(JmsService.class);

	@Resource(mappedName = "java:/jms/queue/HsportsQueue")
	private Queue hsportsQueue;

	@Inject
	@JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext context;

	public void send(String message) {
		try {
			LOG.info("Sending message to the queue: " + message);
			TextMessage textMessage = context.createTextMessage(message);
			context.createProducer().send(this.hsportsQueue, textMessage);
			LOG.info("Message sent to queue.");
		} catch (Exception e) {
			LOG.error(e);
		}
	}
}
