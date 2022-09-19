package com.linkedin.jsf;

import java.time.LocalDateTime;

import javax.enterprise.inject.Produces;

public class InvetoryServiceFactory {

	
	public InventoryService createInstance() {
		InventoryService inventoryService = null;

		if (LocalDateTime.now().getHour() > 12) {
			inventoryService = new LocalInventoryService();
		} else {
			inventoryService = new RemoteInventoryService();
		}

		return inventoryService;
	}
}
