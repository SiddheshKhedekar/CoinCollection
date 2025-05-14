package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.exception.ResourceNotFoundException;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Optional<Currency> getCurrencyById(Long id) {
        return Optional.ofNullable(currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + id)));
    }

    @Override
    public Optional<Currency> getCurrencyByCode(String code) {
        return Optional.ofNullable(currencyRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with code: " + code)));
    }

    @Override
    public Currency createCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public Currency updateCurrency(Long id, Currency currencyDetails) {
        Currency currency = getCurrencyById(id).get(); // this will throw if not found
        currency.setCode(currencyDetails.getCode());
        currency.setDescription(currencyDetails.getDescription());
        return currencyRepository.save(currency);
    }

    @Override
    public void deleteCurrency(Long id) {
        Currency currency = getCurrencyById(id).get(); // this will throw if not found
        currencyRepository.delete(currency);
    }
}
