package org.example.kardex.service.impl;

import org.example.kardex.domain.Product;
import org.example.kardex.domain.dto.ProductDto;
import org.example.kardex.repositories.ProductRepository;
import org.example.kardex.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Optional<ProductDto> getById(Long id) {
		return Optional.ofNullable(mapperProductDto(productRepository.getOne(id)));
	}

	@Override
	public List<ProductDto> getAll() {
		return productRepository.findAll()
			.parallelStream()
			.map(this::mapperProductDto)
			.collect(Collectors.toList());
	}

	@Override
	public ProductDto saveProduct(ProductDto pro) {
		return mapperProductDto(productRepository.saveAndFlush(mapperProduct(pro)));
	}

	@Override
	public ProductDto update(ProductDto pro) {
		return mapperProductDto(productRepository.save(mapperProduct(pro)));
	}

	@Override
	public ProductDto updateStock(Long idProduct, Long count) {
		Optional<ProductDto> product = this.getById(idProduct);
		ProductDto[] productDto = new ProductDto[1];
		product.ifPresent(
			pdto -> {
				pdto.setCount(pdto.getCount() - count);
				productDto[0] = this.update(pdto);
			}
		);
		return productDto[0];
	}

	private Product mapperProduct(ProductDto pro){
		return Product.builder()
			.id(pro.getId())
			.name(pro.getName())
			.description(pro.getDescription())
			.count(pro.getCount())
			.price(pro.getPrice())
			.updateTime(LocalDateTime.now())
			.build();
	}

	private ProductDto mapperProductDto(Product pro){
		return ProductDto.builder()
			.id(pro.getId())
			.name(pro.getName())
			.description(pro.getDescription())
			.count(pro.getCount())
			.price(pro.getPrice())
			.build();
	}
}
