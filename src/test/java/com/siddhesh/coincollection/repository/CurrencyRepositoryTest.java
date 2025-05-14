package com.siddhesh.coincollection.repository;

import com.siddhesh.coincollection.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    void testSaveAndFindByCode() {
        Currency currency = new Currency("GBP", "British Pound");
        currencyRepository.save(currency);

        Optional<Currency> found = currencyRepository.findByCode("GBP");
        assertTrue(found.isPresent());
        assertEquals("British Pound", found.get().getDescription());
    }
}
