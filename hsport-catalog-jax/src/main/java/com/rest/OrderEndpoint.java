package com.rest;

import java.io.StringWriter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.linkedin.InventoryItem;
import com.linkedin.JmsService;
import com.linkedin.Order;

import org.jboss.logging.Logger;

@RequestScoped
@Path("/order")
@Produces("application/json")
@Consumes("application/json")
public class OrderEndpoint {

	private static Logger LOG = Logger.getLogger(OrderEndpoint.class);

	@Inject
	private JmsService jmsService;

	@POST
	public void placeOrder(Order order) {

		StringWriter writer = new StringWriter();

		try (JsonGenerator generator = Json.createGenerator(writer)) {
			generator.writeStartObject();
			generator.write("orderId", order.getOrderId());
			generator.write("storeName", order.getStoreName());

			generator.writeStartArray("items");

			for (InventoryItem inventoryItem : order.getInventoryItems()) {
				generator.writeStartObject();
				generator.write("name", inventoryItem.getName());
				generator.write("quatity", inventoryItem.getQuantity());
				generator.writeEnd();
			}

			generator.writeEnd();

			generator.writeStartObject("customer");
			generator.write("firstName", order.getCustomer().getFirstName());
			generator.write("lastName", order.getCustomer().getLastName());
			generator.writeEnd();

			generator.writeEnd();

			generator.flush();

			jmsService.send(writer.toString());
		}
	}

}
