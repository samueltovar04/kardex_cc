package org.example.kardex.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.example.kardex.domain.Purchase;
import org.example.kardex.domain.dto.ProductDto;
import org.example.kardex.repositories.PurchaseDetailRepository;
import org.example.kardex.repositories.PurchaseRepository;
import org.example.kardex.service.ProductService;
import org.example.kardex.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;
	private final PurchaseDetailRepository detailRepository;
	private final ProductService productService;

	@Autowired
	public PurchaseServiceImpl(
		final PurchaseRepository purchaseRepository,
		final PurchaseDetailRepository detailRepository,
		final ProductService productService) {
		this.purchaseRepository = purchaseRepository;
		this.detailRepository = detailRepository;
		this.productService = productService;
	}

	@Override
	public List<Purchase> getAll() {
		return purchaseRepository.findAll();
	}

	@Override
	public Purchase getById(Long idCompra) {
		return purchaseRepository.findOne(idCompra);
	}

	@Override
	@Transactional
	public Purchase buyProduct(Purchase buying) {

		buying.setDate(LocalDateTime.now());

		// Calcular el total de los productos
		buying.setTotal(
			buying.getPurchaseDetailList()
				.stream()
				.mapToDouble(detail -> {
					// Calcula precio del producto * cantidad
					Optional<ProductDto> product = productService.getById(detail.getProduct().getId());
					if(product.isPresent())
					{
						detail.setPrice(product.get().getPrice());
						detail.setTotal(product.get().getPrice() * detail.getCount());
					}
					return detail.getTotal();
				})
				.sum()
		);

		// Actualizar stock de cada producto
		buying.getPurchaseDetailList()
			.forEach(detail -> productService.updateStock(detail.getProduct().getId(), detail.getCount()));

		purchaseRepository.save(buying);

		buying.getPurchaseDetailList().forEach(detailbuy -> {
			detailbuy.setPurchase(buying);
			detailRepository.save(detailbuy);
		});
		return buying;
	}
}
