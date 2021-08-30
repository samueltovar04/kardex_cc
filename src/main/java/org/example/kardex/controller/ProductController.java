package org.example.kardex.controller;

import java.util.ArrayList;
import java.util.List;
import org.example.kardex.exception.ExceptionsBussines;
import org.example.kardex.domain.dto.ErrorsDto;
import org.example.kardex.domain.dto.ProductDto;
import org.example.kardex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Products")
public class ProductController {
	private final ProductService productService;

	@Autowired
	public ProductController(final ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public ResponseEntity<List<ProductDto>> getAll() {
		return new ResponseEntity<>(this.productService.getAll(), HttpStatus.ACCEPTED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getById(@PathVariable("id") Long id)
		throws ExceptionsBussines {
		return new ResponseEntity<>(this.productService.getById(id).orElseThrow(
			() -> new ExceptionsBussines(
				getErrors(
					"Producto id " + id + " No existe",
					"kardex-030")
			)
		),
			HttpStatus.ACCEPTED);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ProductDto> save(@RequestBody ProductDto item) {
		return new ResponseEntity<>(this.productService.saveProduct(item), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ProductDto> update(@RequestBody ProductDto item) {
		return new ResponseEntity<>(this.productService.update(item), HttpStatus.ACCEPTED);
	}

	private List<ErrorsDto> getErrors(String msg, String codeError){
		List<ErrorsDto> errors = new ArrayList<>();
		final ErrorsDto error = new ErrorsDto();

		error.setMessage(msg);
		error.setErrorCode(codeError);
		errors.add(error);
		return errors;
	}
}

