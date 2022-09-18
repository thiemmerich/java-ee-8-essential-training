package com.linkedin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class Catalog
 */
@Singleton
@LocalBean
public class Catalog implements CatalogLocal {

	private List<CatalogItem> items = new ArrayList<>();

	/**
	 * Default constructor.
	 */
	public Catalog() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CatalogItem> getItems() {
		return this.items;
	}

	@Override
	public void addItem(CatalogItem item) {
		this.items.add(item);
	}

}
