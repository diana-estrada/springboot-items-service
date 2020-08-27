package com.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.commons.models.*;

@FeignClient(name="service-products")
public interface ProductClientRest {

	@GetMapping("/products/")
	public List<Product> list();
	
	@GetMapping("/products/{id}")
	public Product productDetail(@PathVariable Integer id);
	
	@PostMapping("/products/")
	public Product save	(@RequestBody Product product);
	
	@PutMapping("/products/{id}")
	public Product update(@RequestBody Product product, @PathVariable Integer id);
	
	@DeleteMapping("/products/{id}")
	public void delete(@PathVariable Integer id);
	
}
