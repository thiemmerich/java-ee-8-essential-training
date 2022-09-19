package com.linkedin.jsf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Named;

import org.jboss.logging.Logger;

@ApplicationScoped
@RemoteService
@Alternative
public class RemoteInventoryService implements InventoryService {

	private static final long serialVersionUID = 7537143093264901851L;
	private static final Logger LOG = Logger.getLogger(RemoteInventoryService.class);
	private Map<Long, InventoryItem> items = new HashMap<>();

	@Override
	public void createItem(Long catalogItemId, String name) {
		long inventoryItemId = items.size() + 1;
		this.items.put(catalogItemId, new InventoryItem(inventoryItemId, catalogItemId, name, 0L));
		this.printInventory();
	}

	private void printInventory() {
		LOG.info("Remote inventory contains ["+this.items.size()+"] -> ");

		for (Entry<Long, InventoryItem> entry : this.items.entrySet()) {
			LOG.info("Entry: " + entry.getValue().getName());
		}

	}

	@Override
	public Long getQuantity(Long catalogItemId) {
		return 0L;
	}

}
