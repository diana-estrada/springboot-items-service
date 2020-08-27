package com.app.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.item.clients.ProductClientRest;
import com.app.item.model.Item;
import com.app.commons.models.*;

@Service("serviceFeign")
//@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductClientRest clientFeign;

	@Override
	public List<Item> findAll() {
		
		return clientFeign.list().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Integer id, Integer quantity) {
		
		return new Item(clientFeign.productDetail(id), quantity);
	}

	@Override
	public Product save(Product product) {
		return clientFeign.save(product);
	}

	@Override
	public Product update(Product product, Integer id) {
		return clientFeign.update(product, id);
	}

	@Override
	public void delete(Integer id) {
		clientFeign.delete(id);
		
	}

}
