package com.siddhesh.coincollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Currency currency;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController).build();
        currency = new Currency("USD", "United States Dollar");
        currency.setId(1L);
    }

    @Test
    void testCreateCurrency() throws Exception {
        when(currencyService.createCurrency(any(Currency.class))).thenReturn(currency);

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    void testUpdateCurrency() throws Exception {
        when(currencyService.updateCurrency(eq(1L), any(Currency.class))).thenReturn(currency);

        mockMvc.perform(put("/api/currencies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(currency)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    void testDeleteCurrency() throws Exception {
        doNothing().when(currencyService).deleteCurrency(1L);

        mockMvc.perform(delete("/api/currencies/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetCurrencyByCode_found() throws Exception {
        when(currencyService.getCurrencyByCode("USD")).thenReturn(Optional.of(currency));

        mockMvc.perform(get("/api/currencies/USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"))
                .andExpect(jsonPath("$.description").value("United States Dollar"));
    }

    @Test
    void testGetCurrencyByCode_notFound() throws Exception {
        when(currencyService.getCurrencyByCode("XYZ")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/currencies/XYZ"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllCurrencies() throws Exception {
        when(currencyService.getAllCurrencies()).thenReturn(List.of(currency));

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[0].description").value("United States Dollar"));
    }
}
