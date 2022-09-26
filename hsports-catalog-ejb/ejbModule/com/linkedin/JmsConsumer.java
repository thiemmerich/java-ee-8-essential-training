package com.linkedin;

import java.io.StringReader;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPointer;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

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

			String json = message.getBody(String.class);

			try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {

				JsonObject jsonObject = jsonReader.readObject();

				JsonPointer pointer = Json.createPointer("/items/0/quantity");

				long quantity = Long.parseLong(pointer.getValue(jsonObject).toString());

				LOG.info("Quantity found on Json: " + quantity);

				jsonObject = Json.createPatchBuilder()
						.add("/promo", "double")
						.replace("/items/0/quantity", Long.toString(quantity * 2L))
						.remove("/storeName").build()
						.apply(jsonObject);

				LOG.info("Modified json: " + jsonObject);
			}

		} catch (Exception e) {
			LOG.error("Error on receiving the message: " + e);
		}
	}

}
