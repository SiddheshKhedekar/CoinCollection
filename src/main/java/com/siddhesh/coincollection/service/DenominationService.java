package com.siddhesh.coincollection.service;

import com.siddhesh.coincollection.model.Denomination;
import java.util.List;
import java.util.Optional;

public interface DenominationService {
    Denomination createDenomination(Denomination denomination);
    Denomination updateDenomination(Long id, Denomination denomination);
    void deleteDenomination(Long id);
    Optional<Denomination> getDenominationById(Long id);
    List<Denomination> getByValue(Double value);
    List<Denomination> getBySeries(String series);
    List<Denomination> getByValueAndSeries(Double value, String series);
    List<Denomination> getAllDenominations();
}