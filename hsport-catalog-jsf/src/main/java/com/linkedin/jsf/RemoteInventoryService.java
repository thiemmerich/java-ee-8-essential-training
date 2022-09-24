package com.linkedin.jsf;

import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

@ApplicationScoped
@RemoteService
public class RemoteInventoryService implements InventoryService {

	private static final long serialVersionUID = 7537143093264901851L;
	private static final Logger LOG = Logger.getLogger(RemoteInventoryService.class);

	private String apiUrl = "http://localhost:8080/hsports-catalog-jax/hsports/api/";

	@Override
	public void createItem(Long catalogItemId, String name) {
		LOG.info("Starting the request to end-point inventoryitems...");
		Client client = ClientBuilder.newClient();
		Response response = client.target(apiUrl)
				.path("inventoryitems")
				.request().post(Entity.json(new InventoryItem(null, catalogItemId, name, new Random().nextLong())));

		LOG.info("Response Status: " + response.getStatus());
		LOG.info("Response Path:" + response.getLocation().getPath());
	}

	@Override
	public Long getQuantity(Long catalogItemId) {
		Client client = ClientBuilder.newClient();
		InventoryItem inventoryItem = client.target(apiUrl)
				.path("inventoryitems")
				.path("catalog")
				.path("{catalogItemId}")
				.resolveTemplate("catalogItemId", catalogItemId.toString())
				.request().get(InventoryItem.class);

		return inventoryItem.getQuantity();
	}

	@Override
	public Future<InventoryItem> asyncGetQuantity(Long catalogItemId) {
		Client client = ClientBuilder.newClient();
		return client.target(apiUrl)
				.path("inventoryitems")
				.path("catalog")
				.path("{catalogItemId}")
				.resolveTemplate("catalogItemId", catalogItemId.toString())
				.request()
				.async().get(InventoryItem.class);
	}

	@Override
	public CompletionStage<InventoryItem> reactiveGetQuantity(Long catalogItemId) {
		Client client = ClientBuilder.newClient();
		return client.target(apiUrl)
				.path("inventoryitems")
				.path("catalog")
				.path("{catalogItemId}")
				.resolveTemplate("catalogItemId", catalogItemId.toString())
				.request()
				.rx().get(InventoryItem.class);
	}

}
