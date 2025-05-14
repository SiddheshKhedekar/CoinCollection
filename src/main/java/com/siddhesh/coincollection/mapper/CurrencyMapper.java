package com.siddhesh.coincollection.mapper;

import com.siddhesh.coincollection.dto.CurrencyDTO;
import com.siddhesh.coincollection.model.Currency;

import java.util.stream.Collectors;

public class CurrencyMapper {

    public static CurrencyDTO toDto(Currency currency) {
        return new CurrencyDTO(
                currency.getId(),
                currency.getCode(),
                currency.getDescription(),
                currency.getDenominations() != null
                        ? currency.getDenominations().stream()
                        .map(DenominationMapper::toDto)
                        .collect(Collectors.toList())
                        : null
        );
    }
}
