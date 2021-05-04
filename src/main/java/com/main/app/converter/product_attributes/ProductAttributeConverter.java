package com.main.app.converter.product_attributes;

import com.main.app.domain.dto.product_prominent_attributes.ProductAttributesDTO;
import com.main.app.domain.model.product_prominent_attributes.ProductAttributes;

import java.util.List;
import java.util.stream.Collectors;

public class ProductAttributeConverter {


    private static ProductAttributesDTO entityToDTO(ProductAttributes productAttribute) {
        return ProductAttributesDTO
                .builder()
                .id(productAttribute.getId())
                .attributeId(productAttribute.getAttribute().getId())
                .attributeName(productAttribute.getAttribute().getName())
                .productId(productAttribute.getProduct().getId())
                .prominent(productAttribute.isProminent())
                .attributeValueId(productAttribute.getAttributeValue().getId())
                .attributeValueName(productAttribute.getAttributeValue().getName())
                .build();
    }



    public static List<ProductAttributesDTO> listToDTOList(List<ProductAttributes> productAttributes) {
        return productAttributes
                .stream()
                .map(productAttribute -> entityToDTO(productAttribute))
                .collect(Collectors.toList());
    }



}
