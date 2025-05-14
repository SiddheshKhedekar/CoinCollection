package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.model.Currency;
import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    Currency createCurrency(Currency currency);
    Currency updateCurrency(Long id, Currency currency);
    void deleteCurrency(Long id);
    Optional<Currency> getCurrencyById(Long id);
    Optional<Currency> getCurrencyByCode(String code);
    List<Currency> getAllCurrencies();
}