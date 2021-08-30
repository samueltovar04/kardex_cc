package org.example.kardex.service;

import java.util.List;
import java.util.Optional;
import org.example.kardex.domain.dto.ProductDto;

public interface ProductService {
	Optional<ProductDto> getById(Long id);
	List<ProductDto> getAll();
	ProductDto saveProduct(ProductDto customer);
	ProductDto update(ProductDto customer);
	void updateStock(Long idProduct, Long count);
}
