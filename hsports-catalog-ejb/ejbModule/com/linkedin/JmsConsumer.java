package com.linkedin;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.jboss.logging.Logger;

/**
 * Message-Driven Bean implementation class for: JmsConsumer
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "/jms/queue/HsportsQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = "/jms/queue/HsportsQueue")
public class JmsConsumer implements MessageListener {
	private static final Logger LOG = Logger.getLogger(JmsConsumer.class);

	/**
	 * Default constructor.
	 */
	public JmsConsumer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			LOG.info("Message received from JMS consumer MDB: " + message.getBody(String.class));
		} catch (Exception e) {
			LOG.error("Error on receiving the message: " + e);
		}
	}

}
