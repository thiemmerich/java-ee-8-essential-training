package com.linkedin.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkedin.CatalogItem;
import com.linkedin.CatalogLocal;

import org.jboss.logging.Logger;

@RequestScoped
@Named("catalogItemFormBean")
public class CatalogItemFormBean implements Serializable {

	private static final Logger LOG = Logger.getLogger(CatalogItemFormBean.class);
	private static final long serialVersionUID = 382061028856044729L;

	@Inject
	private CatalogLocal catalogBean;

	@Inject
	private InventoryService inventoryService;

	private String searchText;
	private CatalogItem item = new CatalogItem();
	private List<CatalogItem> items = new ArrayList<>();

	public void searchByName() {
		this.items = this.catalogBean.searchByName(this.searchText);
	}

	public String addItem() {
		// long itemId = this.catalogBean.getItems().size() + 1;

		LOG.info("Creating a new item coping the existing one");
		CatalogItem newItem = this.item;

		LOG.info("Now instanciating a new object to clean the object");
		this.item = new CatalogItem();

		// LOG.info("Setting the id " + itemId + " to the new object");
		// newItem.setItemId(itemId);

		LOG.info("Id set to the new object -> " + newItem.getItemId());
		this.catalogBean.addItem(newItem);
		LOG.info("Item added: " + newItem.toString());

		LOG.info("Creating the item on inventory service...");
		this.inventoryService.createItem(newItem.getItemId(), newItem.getName());

		return "list?faces-redirect=true";
	}

	public void init() {
		this.items = this.catalogBean.getItems();
	}

	public CatalogItem getItem() {
		return item;
	}

	public void setItem(CatalogItem item) {
		this.item = item;
	}

	public List<CatalogItem> getItems() {
		return items;
	}

	public void setItems(List<CatalogItem> items) {
		this.items = items;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
