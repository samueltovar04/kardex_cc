package org.example.kardex.service;

import java.util.List;
import org.example.kardex.domain.Purchase;

public interface PurchaseService {
	Purchase getById(Long id);
	List<Purchase> getAll();
	Purchase buyProduct(Purchase buying);
}
