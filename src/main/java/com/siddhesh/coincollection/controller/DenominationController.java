package com.siddhesh.coincollection.controller;

import com.siddhesh.coincollection.model.Denomination;
import com.siddhesh.coincollection.service.DenominationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denominations")
public class DenominationController {

    @Autowired
    private DenominationService denominationService;

    @PostMapping
    public ResponseEntity<Denomination> create(@RequestBody Denomination denomination) {
        return ResponseEntity.ok(denominationService.createDenomination(denomination));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Denomination> update(@PathVariable Long id, @RequestBody Denomination denomination) {
        return ResponseEntity.ok(denominationService.updateDenomination(id, denomination));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        denominationService.deleteDenomination(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Denomination> getById(@PathVariable Long id) {
        return denominationService.getDenominationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/value/{value}")
    public ResponseEntity<List<Denomination>> getByValue(@PathVariable Double value) {
        return ResponseEntity.ok(denominationService.getByValue(value));
    }

    @GetMapping("/series/{series}")
    public ResponseEntity<List<Denomination>> getBySeries(@PathVariable String series) {
        return ResponseEntity.ok(denominationService.getBySeries(series));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Denomination>> getByValueAndSeries(@RequestParam Double value, @RequestParam String series) {
        return ResponseEntity.ok(denominationService.getByValueAndSeries(value, series));
    }

    @GetMapping
    public ResponseEntity<List<Denomination>> getAll() {
        return ResponseEntity.ok(denominationService.getAllDenominations());
    }
}