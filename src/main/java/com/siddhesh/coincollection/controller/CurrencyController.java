package com.siddhesh.coincollection.controller;

import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<Currency> create(@RequestBody Currency currency) {
        return ResponseEntity.ok(currencyService.createCurrency(currency));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> update(@PathVariable Long id, @RequestBody Currency currency) {
        return ResponseEntity.ok(currencyService.updateCurrency(id, currency));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getByCode(@PathVariable String code) {
        return currencyService.getCurrencyByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Currency>> getAll() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }
}