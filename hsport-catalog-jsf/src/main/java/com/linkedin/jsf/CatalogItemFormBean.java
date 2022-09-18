package com.linkedin.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;

@SessionScoped
@Named("catalogItemFormBean")
public class CatalogItemFormBean implements Serializable {

	private static final Logger LOG = Logger.getLogger(CatalogItemFormBean.class);
	private static final long serialVersionUID = 382061028856044729L;
	private CatalogItem item = new CatalogItem();
	private List<CatalogItem> items = new ArrayList<>();

	public String addItem() {
		long itemId = this.items.size() + 1;

		LOG.info("Creating a new item coping the existing one");
		CatalogItem newItem = this.item;

		LOG.info("Now instanciating a new object to clean the object");
		this.item = new CatalogItem();

		LOG.info("Setting the id " + itemId + " to the new object");
		newItem.setItemId(itemId);

		LOG.info("Id set to the new object -> " + newItem.getItemId());
		this.items.add(newItem);
		LOG.info("Item added: " + newItem.toString());

		return "list?faces-redirect=true";
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

}
