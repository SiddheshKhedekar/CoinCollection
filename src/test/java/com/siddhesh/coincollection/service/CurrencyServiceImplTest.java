package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.exception.ResourceNotFoundException;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {

    private CurrencyRepository currencyRepository;
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        currencyRepository = mock(CurrencyRepository.class);
        currencyService = new CurrencyServiceImpl(currencyRepository);
    }

    @Test
    void testGetCurrencyByCode_found() {
        Currency currency = new Currency("USD", "United States Dollar");
        when(currencyRepository.findByCode("USD")).thenReturn(Optional.of(currency));

        Currency result = currencyService.getCurrencyByCode("USD")
                .orElseThrow(() -> new ResourceNotFoundException("Currency with code USD not found"));
        assertEquals("USD", result.getCode());
    }

    @Test
    void testGetCurrencyByCode_notFound() {
        when(currencyRepository.findByCode("XYZ")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> currencyService.getCurrencyByCode("XYZ"));
    }

    @Test
    void testCreateCurrency() {
        Currency currency = new Currency("INR", "Indian Rupee");
        Currency saved = new Currency("INR", "Indian Rupee");
        saved.setId(1L); // ✅ simulate ID generation by the DB

        when(currencyRepository.save(currency)).thenReturn(saved);

        Currency result = currencyService.createCurrency(currency);
        assertNotNull(result.getId()); // now this passes
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdateCurrency_success() {
        Long id = 1L;
        Currency existing = new Currency("USD", "US Dollar");
        existing.setId(id);

        Currency updatedDetails = new Currency("USD", "United States Dollar");

        when(currencyRepository.findById(id)).thenReturn(Optional.of(existing));
        when(currencyRepository.save(any(Currency.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Currency result = currencyService.updateCurrency(id, updatedDetails);

        assertEquals("USD", result.getCode());
        assertEquals("United States Dollar", result.getDescription());
    }

    @Test
    void testUpdateCurrency_notFound() {
        Long id = 2L;
        Currency updatedDetails = new Currency("EUR", "Euro");

        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.updateCurrency(id, updatedDetails));
    }

    @Test
    void testDeleteCurrency_success() {
        Long id = 1L;
        Currency currency = new Currency("JPY", "Japanese Yen");
        currency.setId(id);

        when(currencyRepository.findById(id)).thenReturn(Optional.of(currency));
        doNothing().when(currencyRepository).delete(currency);

        assertDoesNotThrow(() -> currencyService.deleteCurrency(id));
        verify(currencyRepository, times(1)).delete(currency);
    }

    @Test
    void testDeleteCurrency_notFound() {
        Long id = 3L;
        when(currencyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.deleteCurrency(id));
    }
}
