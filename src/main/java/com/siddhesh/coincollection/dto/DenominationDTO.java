package com.siddhesh.coincollection.dto;

public record DenominationDTO(
        Long id,
        String series,
        Double value,
        String imageUrl,
        Long currencyId
) {}
