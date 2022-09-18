package com.linkedin.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.linkedin.CatalogItem;
import com.linkedin.CatalogLocal;

import org.jboss.logging.Logger;

@SessionScoped
@Named("catalogItemFormBean")
public class CatalogItemFormBean implements Serializable {
	private static final Logger LOG = Logger.getLogger(CatalogItemFormBean.class);
	private static final long serialVersionUID = 382061028856044729L;
	
	@EJB
	private CatalogLocal catalogBean;
	
	private CatalogItem item = new CatalogItem();
	private List<CatalogItem> items = new ArrayList<>();

	public String addItem() {
		long itemId = this.catalogBean.getItems().size() + 1;

		LOG.info("Creating a new item coping the existing one");
		CatalogItem newItem = this.item;

		LOG.info("Now instanciating a new object to clean the object");
		this.item = new CatalogItem();

		LOG.info("Setting the id " + itemId + " to the new object");
		newItem.setItemId(itemId);

		LOG.info("Id set to the new object -> " + newItem.getItemId());
		this.catalogBean.addItem(newItem);
		LOG.info("Item added: " + newItem.toString());

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

}
