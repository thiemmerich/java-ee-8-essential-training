package com.rest;

import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.linkedin.InventoryItem;
import com.linkedin.JmsService;

import org.jboss.logging.Logger;

@RequestScoped
@Path("/inventoryitems")
@Produces("application/json")
@Consumes("application/json")
public class InventoryItemEndpoint {

	private static final Logger LOG = Logger.getLogger(InventoryItemEndpoint.class);
	@PersistenceContext
	private EntityManager entityManager;

	@Inject
	private JmsService jmsService;

	@Transactional
	@POST
	public Response create(final InventoryItem inventoryitem) {
		LOG.info("Received item on end-point: " + inventoryitem.getName());
		this.entityManager.persist(inventoryitem);

		this.jmsService.send("Sending a test message to queue from end-point: " + inventoryitem.getName());

		return Response.created(UriBuilder.fromResource(InventoryItemEndpoint.class)
				.path(String.valueOf(inventoryitem.getCatalogItemId())).build()).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		InventoryItem inventoryitem = this.entityManager.find(InventoryItem.class, id);

		if (inventoryitem == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		inventoryitem.setQuantity(ThreadLocalRandom.current().nextLong(1, 100));
		return Response.ok(inventoryitem).build();
	}

	@GET
	@Path("/catalog/{catalogItemId}")
	public void asyncFindByCatalogId(@NotNull @PathParam("catalogItemId") Long catalogItemId,
			@Suspended AsyncResponse asyncResponse) {

		new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					LOG.error(e);
				}

				asyncResponse.resume(findByCatalogId(catalogItemId));
			}
		}.start();
	}

	public InventoryItem findByCatalogId(@NotNull @PathParam("catalogItemId") Long catalogItemId) {
		TypedQuery<InventoryItem> query = this.entityManager
				.createQuery("select i from InventoryItem i where i.catalogItemId = :catalogItemId",
						InventoryItem.class)
				.setParameter("catalogItemId", catalogItemId);

		InventoryItem inventoryitem = query.getSingleResult();

		inventoryitem.setQuantity(ThreadLocalRandom.current().nextLong(1, 100));
		return inventoryitem;
	}

}
