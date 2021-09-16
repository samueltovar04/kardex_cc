package org.example.kardex.controller;

import java.util.List;

import org.example.kardex.domain.Purchase;
import org.example.kardex.domain.dto.PurchaseDto;
import org.example.kardex.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Purchases")
public class PurchaseController {
	private final PurchaseService purchaseService;
	@Autowired
	public PurchaseController(final PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}
	@GetMapping
	public ResponseEntity<List<Purchase>> getAll() {
		return new ResponseEntity<>(this.purchaseService.getAll(), HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Purchase> getById(@PathVariable("id") Long idBuy) {
		return new ResponseEntity<>(this.purchaseService.getById(idBuy), HttpStatus.ACCEPTED);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Purchase> buyProduct(@RequestBody PurchaseDto buy) throws Exception {
		return new ResponseEntity<>(this.purchaseService.buyProduct(buy), HttpStatus.CREATED);
	}
}
