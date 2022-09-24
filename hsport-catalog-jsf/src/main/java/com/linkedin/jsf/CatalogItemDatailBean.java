package com.linkedin.jsf;

import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkedin.CatalogItem;
import com.linkedin.CatalogLocal;
import com.linkedin.ItemManager;

import org.jboss.logging.Logger;

@ConversationScoped
@Named("catalogItemDetailBean")
public class CatalogItemDatailBean implements Serializable {

	private static final Logger LOG = Logger.getLogger(CatalogItemDatailBean.class);
	private static final long serialVersionUID = 1L;
	private long itemId;
	private CatalogItem item;
	private Long quantity;

	@Inject
	@RemoteService
	private InventoryService inventoryService;

	@Inject
	private Conversation conversation;

	@Inject
	private CatalogLocal catalogBean;

	private ItemManager manager = new ItemManager();

	public void fetchItem() throws InterruptedException, ExecutionException {
		this.item = this.catalogBean.findItem(this.itemId);

		CountDownLatch latch = new CountDownLatch(1);
		this.inventoryService.reactiveGetQuantity(this.itemId).thenApply(item -> item.getQuantity())
				.thenAccept(quantity -> {
					this.setQuantity(quantity);
					LOG.info("Quantity on reactive response: " + this.getQuantity());
					latch.countDown();
				});

		LOG.info("Completed promise request processing...");

		latch.await();
	}

	public void addManager() {
		this.manager.getCatalogItems().add(this.item);
		this.item.getItemManagers().add(this.manager);

		this.catalogBean.saveItem(this.item);
		this.item = this.catalogBean.findItem(this.itemId);
	}

	@PostConstruct
	public void init() {
		this.conversation.begin();
	}

	@PreDestroy
	public void destroy() {
		this.conversation.end();
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public CatalogItem getItem() {
		return item;
	}

	public void setItem(CatalogItem item) {
		this.item = item;
	}

	public ItemManager getManager() {
		return manager;
	}

	public void setManager(ItemManager manager) {
		this.manager = manager;
	}
}
