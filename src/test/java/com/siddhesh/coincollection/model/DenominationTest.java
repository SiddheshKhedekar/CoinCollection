package com.siddhesh.coincollection.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DenominationTest {

    @Test
    void testDenominationGettersAndSetters() {
        Denomination denomination = new Denomination();

        denomination.setId(1L);
        denomination.setSeries("2001");
        denomination.setValue(1.5);
        denomination.setImageUrl("http://example.com/image.png");

        Currency currency = new Currency("GBP", "British Pound");
        denomination.setCurrency(currency);

        assertEquals(1L, denomination.getId());
        assertEquals("2001", denomination.getSeries());
        assertEquals(1.5, denomination.getValue());
        assertEquals("http://example.com/image.png", denomination.getImageUrl());
        assertEquals("GBP", denomination.getCurrency().getCode());
    }

    @Test
    void testDenominationParameterizedConstructor() {
        Currency currency = new Currency("INR", "Indian Rupee");
        Denomination denomination = new Denomination(2L, "2010", 10.0, "http://img.inr.png", currency);

        assertEquals(2L, denomination.getId());
        assertEquals("2010", denomination.getSeries());
        assertEquals(10.0, denomination.getValue());
        assertEquals("http://img.inr.png", denomination.getImageUrl());
        assertEquals("INR", denomination.getCurrency().getCode());
    }
}
