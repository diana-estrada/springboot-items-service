package com.app.item.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.item.model.Item;
import com.app.commons.models.*;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService{

	@Autowired
	private RestTemplate clientRest;

	@Override
	public List<Item> findAll() {
		
		List<Product> products = Arrays.asList(
				clientRest.getForObject("http://service-products/products/", Product[].class));
		
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Integer id, Integer quantity) {
		Map<String, Integer> pathVariable = new HashMap<String, Integer>();
		pathVariable.put("id", id);
		
		Product product = clientRest.getForObject("http://service-products/products/{id}", Product.class, pathVariable);
		return new Item(product, quantity);
	}

	@Override
	public Product save(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/products/", HttpMethod.POST, body, Product.class);
		return response.getBody();
	}

	@Override
	public Product update(Product product, Integer id) {
		Map<String, Integer> pathVariable = new HashMap<String, Integer>();
		pathVariable.put("id", id);
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/products/{id}", HttpMethod.PUT, body, Product.class, pathVariable);
		return response.getBody();
	}

	@Override
	public void delete(Integer id) {
		Map<String, Integer> pathVariable = new HashMap<String, Integer>();
		pathVariable.put("id", id);
		
		clientRest.delete("http://service-products/products/{id}", HttpMethod.DELETE, pathVariable);
		
	}

}
