package com.main.app.converter.attribute;

import com.main.app.domain.dto.attribute.AttributeDTO;
import com.main.app.domain.model.attribute.Attribute;

import java.util.List;
import java.util.stream.Collectors;

public class AttributeConverter {

    public static Attribute DTOtoEntity(AttributeDTO attributeDTO){
        return Attribute
                .builder()
                .name(attributeDTO.getName())
                .participatesInVariation(attributeDTO.isParticipatesInVariation())
                .enteredManually(attributeDTO.isEnteredManually())
                .build();
    }

    public static AttributeDTO entityToDTO(Attribute attribute){
        return AttributeDTO
                .builder()
                .id(attribute.getId())
                .name(attribute.getName())
                .participatesInVariation(attribute.isParticipatesInVariation())
                .enteredManually(attribute.isEnteredManually())
                .dateCreated(attribute.getDateCreated())
                .build();
    }

    public static List<AttributeDTO> listToDTOList(List<Attribute> attributes) {
        return attributes
                .stream()
                .map(attribute -> entityToDTO(attribute))
                .collect(Collectors.toList());
    }

}
