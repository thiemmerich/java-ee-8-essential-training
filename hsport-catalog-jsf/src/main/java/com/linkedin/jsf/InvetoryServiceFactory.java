package com.linkedin.jsf;

public class InvetoryServiceFactory {

	public InventoryService createInstance() {
		InventoryService inventoryService = new RemoteInventoryService();

		return inventoryService;
	}
}
