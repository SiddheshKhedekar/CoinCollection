package com.siddhesh.coincollection.mapper;

import com.siddhesh.coincollection.dto.DenominationDTO;
import com.siddhesh.coincollection.model.Denomination;

public class DenominationMapper {

    public static DenominationDTO toDto(Denomination denomination) {
        return new DenominationDTO(
                denomination.getId(),
                denomination.getSeries(),
                denomination.getValue(),
                denomination.getImageUrl(),
                denomination.getCurrency().getId()
        );
    }
}
