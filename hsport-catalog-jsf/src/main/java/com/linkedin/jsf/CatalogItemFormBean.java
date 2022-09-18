package com.linkedin.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named("catalogItemFormBean")
public class CatalogItemFormBean {
	private String name;
	private String manufacturer;
	private String sku;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
