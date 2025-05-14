package com.siddhesh.coincollection.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Currency currency;

    @BeforeEach
    void setUp() {
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
