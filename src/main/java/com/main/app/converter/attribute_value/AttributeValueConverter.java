package com.main.app.converter.attribute_value;

import com.main.app.domain.dto.attribute_value.AttributeValueDTO;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.service.attribute.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeValueConverter {

    private static AttributeService attributeService;

    @Autowired
    private AttributeValueConverter(AttributeService attributeService) {
        AttributeValueConverter.attributeService = attributeService;
    }

    public static AttributeValue DTOtoEntity(AttributeValueDTO attributeValueDTO){
        return AttributeValue
                .builder()
                .name(attributeValueDTO.getName())
                .attribute(attributeValueDTO.getAttributeId() != null ? attributeService.getOne(attributeValueDTO.getAttributeId()) : null)
                .build();
    }

    public static AttributeValueDTO entityToDTO(AttributeValue attributeValue){
        return AttributeValueDTO
                .builder()
                .id(attributeValue.getId())
                .name(attributeValue.getName())
                .attributeId(attributeValue.getAttribute() != null ? attributeValue.getAttribute().getId() : null)
                .attributeName(attributeValue.getAttribute() != null ? attributeValue.getAttribute().getName() : null)
                .dateCreated(attributeValue.getDateCreated())
                .build();
    }

    public static List<AttributeValueDTO> listToDTOList(List<AttributeValue> attributeValues) {
        return attributeValues
                .stream()
                .map(attributeValue -> entityToDTO(attributeValue))
                .collect(Collectors.toList());
    }

}
