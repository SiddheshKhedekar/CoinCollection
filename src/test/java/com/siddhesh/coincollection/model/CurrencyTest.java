package com.siddhesh.coincollection.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyTest {

    @Test
    void testCurrencyGettersAndSetters() {
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("USD");
        currency.setDescription("US Dollar");

        Denomination denom1 = new Denomination();
        Denomination denom2 = new Denomination();
        List<Denomination> denominations = List.of(denom1, denom2);
        currency.setDenominations(denominations);

        assertEquals(1L, currency.getId());
        assertEquals("USD", currency.getCode());
        assertEquals("US Dollar", currency.getDescription());
        assertEquals(2, currency.getDenominations().size());
    }

    @Test
    void testCurrencyParameterizedConstructor() {
        Currency currency = new Currency("EUR", "Euro");

        assertEquals("EUR", currency.getCode());
        assertEquals("Euro", currency.getDescription());
    }
}
