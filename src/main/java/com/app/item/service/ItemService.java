package com.app.item.service;

import java.util.List;

import com.app.item.model.Item;
import com.app.commons.models.*;

public interface ItemService {

	public List<Item> findAll();

	public Item findById(Integer id, Integer quantity);
	
	public Product save(Product product);
	
	public Product update(Product product, Integer id);
	
	public void delete(Integer id);
}
