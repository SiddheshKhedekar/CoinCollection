package com.siddhesh.coincollection.repository;

import com.siddhesh.coincollection.model.Denomination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DenominationRepository extends JpaRepository<Denomination, Long> {
    List<Denomination> findByValue(Double value);
    List<Denomination> findBySeries(String series);
    List<Denomination> findByValueAndSeries(Double value, String series);
}
