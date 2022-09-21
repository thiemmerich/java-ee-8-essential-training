package com.linkedin;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

/**
 * Session Bean implementation class Catalog
 */
@Singleton
@LocalBean
public class Catalog implements CatalogLocal {

	@PersistenceContext
	private EntityManager entityManager;
	private static final Logger LOG = Logger.getLogger(Catalog.class);
	// private List<CatalogItem> items = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Catalog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CatalogItem> getItems() {
		LOG.info("Searching for the entities on the DB...");
		return this.entityManager.createQuery("select c from CatalogItem c", CatalogItem.class).getResultList();
	}

	@Override
	public void addItem(CatalogItem item) {
		LOG.info("Persisting a new object on DB: " + item.getName());
		this.entityManager.persist(item);
	}

	@Override
	public CatalogItem findItem(Long itemId) {
		return this.entityManager.find(CatalogItem.class, itemId);
	}

	@Override
	public void deleteItem(CatalogItem item) {
		this.entityManager.remove(this.entityManager.contains(item) ? item : this.entityManager.merge(item));

	}

	@Override
	public List<CatalogItem> searchByName(String name) {
		return this.entityManager.createQuery("select c from CatalogItem c where c.name like :name", CatalogItem.class)
				.setParameter("name", "%" + name + "%").getResultList();
	}

	@Override
	public void saveItem(CatalogItem item) {
		this.entityManager.merge(item);
	}

}
