package com.app.item.model;

import com.app.commons.models.Product;

public class Item {
	
	private Product product;
	private Integer quantiy;

	public Item() {
	}

	public Item(Product product, Integer quantiy) {
		super();
		this.product = product;
		this.quantiy = quantiy;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantiy() {
		return quantiy;
	}

	public void setQuantiy(Integer quantiy) {
		this.quantiy = quantiy;
	}
	
	public Double getTotal() {
		return product.getPrice() * quantiy;
	}

}
