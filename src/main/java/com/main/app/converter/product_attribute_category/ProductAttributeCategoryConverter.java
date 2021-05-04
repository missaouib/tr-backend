package com.main.app.converter.product_attribute_category;

import com.main.app.domain.dto.product_attribute_category.ProductAttributeCategoryDTO;
import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductAttributeCategoryConverter {

    private static ProductAttributeCategoryDTO entityToDTO(ProductAttributeCategory productAttributeCategory) {
        return ProductAttributeCategoryDTO
                .builder()
                .id(productAttributeCategory.getId())
                .name(productAttributeCategory.getName())
                .attributeId(productAttributeCategory.getAttributeValue().getAttribute().getId())
                .attributeName(productAttributeCategory.getAttributeValue().getAttribute().getName())
                .attributeValueId(productAttributeCategory.getAttributeValue().getId())
                .attributeValueName(productAttributeCategory.getAttributeValue().getName())
                .build();
    }


    public static List<ProductAttributeCategoryDTO> listToDTOList(List<ProductAttributeCategory> productAttributeCategories) {
        return productAttributeCategories
                .stream()
                .map(productAttributeCategory -> entityToDTO(productAttributeCategory))
                .collect(Collectors.toList());
    }



}
