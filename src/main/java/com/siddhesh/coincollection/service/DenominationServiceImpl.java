package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.exception.ResourceNotFoundException;
import com.siddhesh.coincollection.model.Currency;
import com.siddhesh.coincollection.model.Denomination;
import com.siddhesh.coincollection.repository.CurrencyRepository;
import com.siddhesh.coincollection.repository.DenominationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenominationServiceImpl implements DenominationService {

    private final DenominationRepository denominationRepository;
    private final CurrencyRepository currencyRepository;

    public DenominationServiceImpl(DenominationRepository denominationRepository,
                                   CurrencyRepository currencyRepository) {
        this.denominationRepository = denominationRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Denomination> getAllDenominations() {
        return denominationRepository.findAll();
    }

    @Override
    public Optional<Denomination> getDenominationById(Long id) {
        return Optional.ofNullable(denominationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Denomination not found with id: " + id)));
    }

    @Override
    public List<Denomination> getByValue(Double value) {
        return denominationRepository.findByValue(value);
    }

    @Override
    public List<Denomination> getBySeries(String series) {
        return denominationRepository.findBySeries(series);
    }

    @Override
    public List<Denomination> getByValueAndSeries(Double value, String series) {
        return denominationRepository.findByValueAndSeries(value, series);
    }

    @Override
    public Denomination createDenomination(Denomination denomination) {
        Currency currency = currencyRepository.findById(denomination.getCurrency().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with id: " + denomination.getCurrency().getId()));
        return denominationRepository.save(denomination);
    }

    @Override
    public Denomination updateDenomination(Long id, Denomination updated) {
        Optional<Denomination> existingOptional = getDenominationById(id);
        if (existingOptional.isPresent()) {
            Denomination existing = existingOptional.get();
            Denomination updatedDenomination = new Denomination(
                    existing.getId(),
                    updated.getSeries(),
                    updated.getValue(),
                    updated.getImageUrl(),
                    updated.getCurrency()
            );
            return denominationRepository.save(updatedDenomination);
        } else {
            throw new ResourceNotFoundException("Denomination not found with id: " + id);
        }
    }

    @Override
    public void deleteDenomination(Long id) {
        Optional<Denomination> optionalDenomination = getDenominationById(id);
        if (optionalDenomination.isPresent()) {
            Denomination denomination = optionalDenomination.get();
            denominationRepository.delete(denomination);
        } else {
            throw new ResourceNotFoundException("Denomination not found with id: " + id);
        }
    }
}
