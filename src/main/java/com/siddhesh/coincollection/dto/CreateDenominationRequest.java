package com.siddhesh.coincollection.dto;

public record CreateDenominationRequest(
        String series,
        Double value,
        String imageUrl,
        Long currencyId
) {}
