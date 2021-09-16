package org.example.kardex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kardex.domain.Purchase;
import org.example.kardex.domain.PurchaseDetail;
import org.example.kardex.domain.dto.PurchaseDto;
import org.example.kardex.domain.dto.UserDto;
import org.example.kardex.exception.InsufficientStockException;
import org.example.kardex.repositories.PurchaseDetailRepository;
import org.example.kardex.repositories.PurchaseRepository;
import org.example.kardex.service.ProductService;
import org.example.kardex.service.PurchaseService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.example.kardex.domain.Data.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseService purchaseService;

    @MockBean
    private ProductService productService;

    @MockBean
    private PurchaseRepository purchaseRepository;

    @MockBean
    private PurchaseDetailRepository detailRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    private String token;

    @Test
    public void testGetById() throws Exception {

        when(purchaseService.getById(1L)).thenReturn(createPurchase001().orElseThrow(() -> new Exception()));

        mockMvc.perform(get("/api/v1/Purchases/1").header("Authorization", this.getToken()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.total").value(3500))
                .andExpect(jsonPath("$.customer.userName").value("soderling"))
                .andExpect(jsonPath("$.customer.password").value("abc123"))
                .andExpect(jsonPath("$.purchaseDetailList[0].count").value(10))
                .andExpect(jsonPath("$.purchaseDetailList[1].count").value(20))
                .andExpect(jsonPath("$.id").value(1));

        verify(purchaseService).getById(1L);
    }

    @Test
    public void testGetAll() throws Exception {

        List<Purchase> purchases = Arrays.asList(createPurchase001().orElseThrow(() -> new Exception()),
                createPurchase002().orElseThrow(() -> new Exception()));

        when(purchaseService.getAll()).thenReturn(purchases);

        mockMvc.perform(get("/api/v1/Purchases").header("Authorization", this.getToken()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$[0].total").value(3500))
                .andExpect(jsonPath("$[1].total").value(3500))
                .andExpect(jsonPath("$[0].customer.userName").value("soderling"))
                .andExpect(jsonPath("$[1].customer.userName").value("soderling"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(purchases)));

        verify(purchaseService).getAll();
    }

    @Test
    public void testBuyProduct() throws Exception {

        PurchaseDto PurchaseDtoRequest = new PurchaseDto(1L,2500.00, createCustomerRequest001().get(), createPurchaseDetailListRequestOneItem());

        String json = objectMapper.writeValueAsString(PurchaseDtoRequest);

        Optional<Purchase> purchaseResponse = Optional.of(new Purchase(1L, LocalDateTime.now(), 2500.00, createCustomer001().get(), createPurchaseDetailListOneItem(), LocalDateTime.now(), LocalDateTime.now()));

        String jsonPurchaseResponse = objectMapper.writeValueAsString(purchaseResponse.get());

        when(productService.getById(1L)).thenReturn(createProductDto001());
        when(purchaseService.buyProduct(any(PurchaseDto.class))).thenReturn(purchaseResponse.get());

        mockMvc.perform(post("/api/v1/Purchases").header("Authorization", this.getToken()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(objectMapper.writeValueAsString(purchaseResponse.get())));

        verify(purchaseService).buyProduct(PurchaseDtoRequest);
    }

    @Test
    public void testBuyProductOutOfStock() throws Exception {

        PurchaseDto PurchaseDtoRequest = new PurchaseDto(1L,1500.00, createCustomerRequest001().get(), createPurchaseDetailListRequestOneItem());

        String json = objectMapper.writeValueAsString(PurchaseDtoRequest);

        doThrow(InsufficientStockException.class).when(purchaseService).buyProduct(PurchaseDtoRequest);

        mockMvc.perform(post("/api/v1/Purchases").header("Authorization", this.getToken()).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());

        verify(purchaseService).buyProduct(PurchaseDtoRequest);
    }

    private String getToken() throws Exception {

        UserDto userDtoRequest = new UserDto(1L, "soderling", "abc123");

        String json = objectMapper.writeValueAsString(userDtoRequest);

        if (this.token == null) {
            ResultActions auth = mockMvc
                    .perform(post("/api/v1/Users/Login").contentType(MediaType.APPLICATION_JSON).content(json))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            assertNotNull(auth);

            this.token = "Bearer " + auth.andReturn().getResponse().getContentAsString().split("Bearer")[1].replace("\"", "")
                    .replace("}", "");
        }

        return this.token;
    }

}
