package com.siddhesh.coincollection.dto;

import java.util.List;

public record CurrencyDTO(
        Long id,
        String code,
        String description,
        List<DenominationDTO> denominations
) {}
