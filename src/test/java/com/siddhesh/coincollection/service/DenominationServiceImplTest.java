package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.exception.ResourceNotFoundException;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.model.Denomination;
import com.siddhesh.coincollection.repository.CurrencyRepository;
import com.siddhesh.coincollection.repository.DenominationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DenominationServiceImplTest {

    @Mock
    private DenominationRepository denominationRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private DenominationServiceImpl denominationService;

    private Currency currency;
    private Denomination denomination;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        currency = new Currency("INR", "Indian Rupee");
        currency.setId(1L);

        denomination = new Denomination(1L, "Series A", 5.0, "http://example.com/img.png", currency);
    }

    @Test
    void testGetAllDenominations() {
        when(denominationRepository.findAll()).thenReturn(List.of(denomination));

        List<Denomination> result = denominationService.getAllDenominations();

        assertEquals(1, result.size());
        verify(denominationRepository, times(1)).findAll();
    }

    @Test
    void testGetDenominationById_Found() {
        when(denominationRepository.findById(1L)).thenReturn(Optional.of(denomination));

        Optional<Denomination> result = denominationService.getDenominationById(1L);

        assertTrue(result.isPresent());
        assertEquals("Series A", result.get().getSeries());
    }

    @Test
    void testGetDenominationById_NotFound() {
        when(denominationRepository.findById(2L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                denominationService.getDenominationById(2L));
        assertEquals("Denomination not found with id: 2", ex.getMessage());
    }

    @Test
    void testGetByValue() {
        when(denominationRepository.findByValue(5.0)).thenReturn(List.of(denomination));

        List<Denomination> result = denominationService.getByValue(5.0);

        assertEquals(1, result.size());
        assertEquals(5.0, result.get(0).getValue());
    }

    @Test
    void testGetBySeries() {
        when(denominationRepository.findBySeries("Series A")).thenReturn(List.of(denomination));

        List<Denomination> result = denominationService.getBySeries("Series A");

        assertEquals(1, result.size());
        assertEquals("Series A", result.get(0).getSeries());
    }

    @Test
    void testGetByValueAndSeries() {
        when(denominationRepository.findByValueAndSeries(5.0, "Series A"))
                .thenReturn(List.of(denomination));

        List<Denomination> result = denominationService.getByValueAndSeries(5.0, "Series A");

        assertEquals(1, result.size());
    }

    @Test
    void testCreateDenomination_Success() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.of(currency));
        when(denominationRepository.save(denomination)).thenReturn(denomination);

        Denomination result = denominationService.createDenomination(denomination);

        assertEquals("Series A", result.getSeries());
    }

    @Test
    void testCreateDenomination_CurrencyNotFound() {
        when(currencyRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                denominationService.createDenomination(denomination));

        assertEquals("Currency not found with id: 1", ex.getMessage());
    }

    @Test
    void testUpdateDenomination_Success() {
        Denomination updated = new Denomination(null, "Series B", 10.0, "new-url", currency);

        when(denominationRepository.findById(1L)).thenReturn(Optional.of(denomination));
        when(denominationRepository.save(any(Denomination.class))).thenReturn(updated);

        Denomination result = denominationService.updateDenomination(1L, updated);

        assertEquals("Series B", result.getSeries());
        assertEquals(10.0, result.getValue());
    }

    @Test
    void testUpdateDenomination_NotFound() {
        when(denominationRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                denominationService.updateDenomination(1L, denomination));

        assertEquals("Denomination not found with id: 1", ex.getMessage());
    }

    @Test
    void testDeleteDenomination_Success() {
        when(denominationRepository.findById(1L)).thenReturn(Optional.of(denomination));
        doNothing().when(denominationRepository).delete(denomination);

        assertDoesNotThrow(() -> denominationService.deleteDenomination(1L));
        verify(denominationRepository).delete(denomination);
    }

    @Test
    void testDeleteDenomination_NotFound() {
        when(denominationRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () ->
                denominationService.deleteDenomination(1L));

        assertEquals("Denomination not found with id: 1", ex.getMessage());
    }
}
