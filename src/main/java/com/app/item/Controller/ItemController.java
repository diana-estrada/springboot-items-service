package com.app.item.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.item.model.Item;
import com.app.commons.models.*;
import com.app.item.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
public class ItemController {
	
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	private Environment env;
	
	@Value("${configuracion.texto}")
	private String texto;

	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	
	@GetMapping("/items")
	public List<Item> listItems(){
		
		return itemService.findAll();
	}

	@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/items/{id}/quantity/{quantity}")
	public Item getItemById(@PathVariable Integer id, @PathVariable Integer quantity) {
		return itemService.findById(id, quantity);
	}
	
	public Item metodoAlternativo(Integer id, Integer quantity) {
		Item item = new Item();
		Product product = new Product();
	
		item.setQuantiy(quantity);
		
		product.setDescription("Mermelada de fresa");
		product.setId(id);
		product.setMinQuantity(5);
		product.setName("McCormic Mermelada de fresa");
		product.setPrice(10.50);
		product.setQuantity(50);
		product.setType("Alimentos");
		
		item.setProduct(product);
		
		return item;
		
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(){
		log.info(texto);
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/items")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Product save(@RequestBody Product product) {
		return itemService.save(product);
	}
	
	@PutMapping("/items/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Product update(@RequestBody Product product, @PathVariable Integer id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("/items/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		itemService.delete(id);
	}
}
