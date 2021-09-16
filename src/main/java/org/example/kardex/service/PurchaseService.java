package org.example.kardex.service;

import java.util.List;
import org.example.kardex.domain.Purchase;
import org.example.kardex.domain.dto.PurchaseDto;

public interface PurchaseService {
	Purchase getById(Long id);
	List<Purchase> getAll();
	Purchase buyProduct(PurchaseDto buying) throws Exception;
}
