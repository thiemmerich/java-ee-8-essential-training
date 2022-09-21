package com.linkedin.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkedin.CatalogItem;
import com.linkedin.CatalogLocal;

import org.jboss.logging.Logger;

@ConversationScoped
@Named("catalogItemDeleteBean")
public class CatalogItemDeleteBean implements Serializable {

	private static final long serialVersionUID = -7364206536010221465L;
	private static final Logger LOG = Logger.getLogger(CatalogItemDeleteBean.class);
	private long itemId;

	private CatalogItem item;

	@Inject
	private CatalogItemFormBean catalogItemFormBean;

	@Inject
	private CatalogLocal catalogBean;

	@Inject
	private Conversation conversation;

	public void fetchItem() {
		LOG.info("Starting the conversation...");
		this.conversation.begin();

		LOG.info("Searching for the item on DB...");
		this.item = catalogBean.findItem(this.itemId);
	}

	public String removeItem() {
		LOG.info("Deleting the item found...");
		this.catalogBean.deleteItem(this.item);

		LOG.info("Endind the conversation.");
		this.conversation.end();
		return "list?faces-redirect=true";
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public CatalogItem getItem() {
		return item;
	}

	public void setItem(CatalogItem item) {
		this.item = item;
	}

	public CatalogItemFormBean getCatalogItemFormBean() {
		return catalogItemFormBean;
	}

	public void setCatalogItemFormBean(CatalogItemFormBean catalogItemFormBean) {
		this.catalogItemFormBean = catalogItemFormBean;
	}

}
