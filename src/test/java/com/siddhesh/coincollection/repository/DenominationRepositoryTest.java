package com.siddhesh.coincollection.repository;

import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.model.Denomination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DenominationRepositoryTest {

    @Autowired
    private DenominationRepository denominationRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private Currency testCurrency;

    @BeforeEach
    void setUp() {
        testCurrency = new Currency("USD", "United States Dollar");
        currencyRepository.save(testCurrency);

        denominationRepository.save(new Denomination(null, "Series A", 1.0, "image1.jpg", testCurrency));
        denominationRepository.save(new Denomination(null, "Series A", 0.5, "image2.jpg", testCurrency));
        denominationRepository.save(new Denomination(null, "Series B", 1.0, "image3.jpg", testCurrency));
    }

    @Test
    void testFindByValue() {
        List<Denomination> result = denominationRepository.findByValue(1.0);
        assertEquals(2, result.size());
    }

    @Test
    void testFindBySeries() {
        List<Denomination> result = denominationRepository.findBySeries("Series A");
        assertEquals(2, result.size());
    }

    @Test
    void testFindByValueAndSeries() {
        List<Denomination> result = denominationRepository.findByValueAndSeries(1.0, "Series A");
        assertEquals(1, result.size());
        assertEquals("image1.jpg", result.get(0).getImageUrl());
    }
}
