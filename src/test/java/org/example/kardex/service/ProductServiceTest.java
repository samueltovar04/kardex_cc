package org.example.kardex.service;

import org.example.kardex.domain.Product;
import org.example.kardex.domain.dto.ProductDto;
import org.example.kardex.repositories.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.example.kardex.domain.Data.createProduct001;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
    @MockBean
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Test
    public void updateStockTest() {
        Product productSave = new Product(1L, "book", "the best book", 99L, 250.00, LocalDateTime.now(), LocalDateTime.now());

        when(productRepository.getOne(1L)).thenReturn(createProduct001().get());
        when(productRepository.save(any(Product.class))).thenReturn(productSave);

        ProductDto productDto = productService.updateStock(1L, 1L);

        assertEquals(1L, productDto.getId());
        assertEquals(99L, productDto.getCount());
    }
}
