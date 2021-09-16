package org.example.kardex.domain;

import org.example.kardex.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Data {

    public static Optional<Purchase> createPurchase001() {
        return Optional.of(new Purchase(1L, LocalDateTime.now(), 3500.00, createCustomer001().get(), createPurchaseDetailList(), LocalDateTime.now(), LocalDateTime.now()));
    }

    public static Optional<Purchase> createPurchase002() {
        return Optional.of(new Purchase(2L, LocalDateTime.now(), 3500.00, createCustomer001().get(), createPurchaseDetailList(), LocalDateTime.now(), LocalDateTime.now()));
    }

    public static Optional<Purchase> createPurchase003() {
        return Optional.of(new Purchase(3L, LocalDateTime.now(), 1500.00, createCustomer001().get(), createPurchaseDetailList(), LocalDateTime.now(), LocalDateTime.now()));
    }

/*    public static Optional<Purchase> createPurchase003() {
        return Optional.of(new Purchase(3L, LocalDateTime.now(), 1500.00, createCustomer().get(), createPurchaseDetailList().get(), LocalDateTime.now(), LocalDateTime.now()));
    }*/

    public static Optional<Customer> createCustomer001() {
        return Optional.of(new Customer(1L, "soderling", "abc123", "Emanuel", "Sodero", "sodero.emanuel@gmail.com", "1127945540", LocalDateTime.now(), LocalDateTime.now()));
    }

    public static Optional<Customer> createCustomerRequest001() {
        return Optional.of(new Customer(1L, "soderling", "abc123", "Emanuel", "Sodero", "sodero.emanuel@gmail.com", "1127945540", null, null));
    }

    public static Optional<Product> createProduct001() {
        return Optional.of(new Product(1L, "book", "the best book", 100L, 250.00, LocalDateTime.now(), LocalDateTime.now()));
    }

    public static Optional<Product> createProductRequest001() {
        return Optional.of(new Product(1L, "book", "the best book", 100L, 250.00, null, null));
    }

    public static Optional<Product> createProduct002() {
        return Optional.of(new Product(2L, "pencil", "the best pencil", 100L, 50.00, LocalDateTime.now(), LocalDateTime.now()));
    }

    public static Optional<Product> createProductOutOfStock003() {
        return Optional.of(new Product(3L, "paper", "the best paper", 0L, 300.00, null, null));
    }

    public static Optional<ProductDto> createProductDto001() {
        return Optional.of(new ProductDto(1L, "book", "the best book", 100L, 250.00));
    }

    public static Optional<ProductDto> createProductDto002() {
        return Optional.of(new ProductDto(2L, "pencil", "the best pencil", 100L, 50.00));
    }

    public static Optional<ProductDto> createProductDtoOutStock001() {
        return Optional.of(new ProductDto(1L, "book", "the best bokk", 0L, 250.00));
    }

    public static Optional<PurchaseDetail> createPurchaseDetail001() {
        return Optional.of(new PurchaseDetail(1L, 10L, 250.00, 2500.00, createProduct001().get(), null));
    }

    public static Optional<PurchaseDetail> createPurchaseDetailRequest001() {
        return Optional.of(new PurchaseDetail(1L, 10L, 250.00, 2500.00, createProductRequest001().get(), null));
    }

    public static Optional<PurchaseDetail> createPurchaseDetail002() {
        return Optional.of(new PurchaseDetail(2L, 20L, 50.00, 1000.00, createProduct002().get(), null));
    }

    public static Optional<PurchaseDetail> createPurchaseDetailOutOfStock003() {
        return Optional.of(new PurchaseDetail(3L, 10L, 300.00, 300.00, createProductOutOfStock003().get(), createPurchase001().get()));
    }

    public static List<PurchaseDetail> createPurchaseDetailList() {
        List<PurchaseDetail> purchaseDetailList = null;
        try {
            purchaseDetailList = Arrays.asList(createPurchaseDetail001().orElseThrow(() -> new Exception()),
                    createPurchaseDetail002().orElseThrow(() -> new Exception()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchaseDetailList;
    }

    public static List<PurchaseDetail> createPurchaseDetailListOneItem() {
        List<PurchaseDetail> purchaseDetailList = null;
        try {
            purchaseDetailList = Arrays.asList(createPurchaseDetail001().orElseThrow(() -> new Exception()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchaseDetailList;
    }

    public static List<PurchaseDetail> createPurchaseDetailListRequestOneItem() {
        List<PurchaseDetail> purchaseDetailList = null;
        try {
            purchaseDetailList = Arrays.asList(createPurchaseDetailRequest001().orElseThrow(() -> new Exception()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return purchaseDetailList;
    }
}
