package com.main.app.converter.attribute_category;

import com.main.app.converter.attribute_value.AttributeValueConverter;
import com.main.app.domain.dto.attribute_category.AttributeCategoryDTO;
import com.main.app.domain.dto.attribute_category.AttributeCategoryUniqueDTO;
import com.main.app.domain.dto.attribute_value.AttributeValueDTO;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.service.attribute.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeCategoryConverter {

    private static AttributeService attributeService;

    @Autowired
    private AttributeCategoryConverter(AttributeService attributeService) {
        AttributeCategoryConverter.attributeService = attributeService;
    }

    public static AttributeCategory DTOtoEntity(AttributeCategoryDTO attributeCategoryDTO){
        return AttributeCategory
                .builder()
                .name(attributeCategoryDTO.getName())
                .attribute(attributeService.getOne(attributeCategoryDTO.getAttributeId()))
                .attribute_name(attributeCategoryDTO.getAttributeName())
                .build();
    }

    public static AttributeCategoryDTO entityToDTO(AttributeCategory attributeCategory){
        return AttributeCategoryDTO
                .builder()
                .id(attributeCategory.getId())
                .name(attributeCategory.getName())
                .attributeId(attributeCategory.getAttribute().getId() != null ? attributeCategory.getAttribute().getId() : null)
                .attributeName(attributeCategory.getAttribute_name() != null ? attributeCategory.getAttribute_name() : null)
                .dateCreated(attributeCategory.getDateCreated())
                .enteredManually(attributeCategory.getAttribute().isEnteredManually())
                .build();
    }

    public static AttributeCategoryUniqueDTO entityUniqueToDTO(AttributeCategoryUniqueDTO unique){
        return AttributeCategoryUniqueDTO
                .builder()
                .id(unique.getId())
                .name(unique.getName())
                .dateCreated(unique.getDateCreated())
                .build();
    }

    public static List<AttributeCategoryDTO> listToDTOList(List<AttributeCategory> attributeCategories) {
        return attributeCategories
                .stream()
                .map(attributeCategory -> entityToDTO(attributeCategory))
                .collect(Collectors.toList());
    }

    public static List<AttributeCategoryUniqueDTO> listUniqueToDTOList(List<AttributeCategoryUniqueDTO> uniqueDTOS) {
        return uniqueDTOS
                .stream()
                .map(unique -> entityUniqueToDTO(unique))
                .collect(Collectors.toList());
    }



}
