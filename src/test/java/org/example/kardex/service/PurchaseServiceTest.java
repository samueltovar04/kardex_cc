package org.example.kardex.service;

import org.example.kardex.domain.Purchase;
import org.example.kardex.domain.dto.PurchaseDto;
import org.example.kardex.exception.InsufficientStockException;
import org.example.kardex.repositories.PurchaseDetailRepository;
import org.example.kardex.repositories.PurchaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.example.kardex.domain.Data.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseServiceTest {
    @MockBean
    PurchaseRepository purchaseRepository;
    @MockBean
    PurchaseDetailRepository detailRepository;
    @MockBean
    ProductService productService;
    @Autowired
    PurchaseService purchaseService;

    @Test
    public void buyProductTest() throws Exception {
        when(productService.getById(1L)).thenReturn(createProductDto001());
        when(productService.getById(2L)).thenReturn(createProductDto002());

        PurchaseDto purchaseDto = new PurchaseDto(1L, 3500.0, createCustomer001().get(), createPurchaseDetailList());

        Purchase purchase = purchaseService.buyProduct(purchaseDto);

        assertEquals(3500.0, purchase.getTotal());
    }

    @Test
    public void buyProductTestOutOfStock() throws InsufficientStockException {
        when(productService.getById(1L)).thenReturn(createProductDtoOutStock001());
        when(productService.getById(2L)).thenReturn(createProductDto002());

        PurchaseDto purchaseDto = new PurchaseDto(1L, 1500.0, createCustomer001().get(), createPurchaseDetailList());

        Exception exception = assertThrows(InsufficientStockException.class, () -> purchaseService.buyProduct(purchaseDto));

        String expectedMessage = "There is not enough stock of the ";

        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }



}
