package com.main.app.converter.variation;

import com.main.app.converter.attribute_value.AttributeValueConverter;
import com.main.app.domain.dto.variation.VariationDTO;
import com.main.app.domain.model.variation.Variation;
import com.main.app.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VariationConverter {

    private static ProductService productService;

    @Autowired
    private VariationConverter(ProductService productService) {
        VariationConverter.productService = productService;
    }

    public static Variation DTOtoEntity(VariationDTO variationDTO){
        return Variation
                .builder()
                .slug(variationDTO.getSlug())
                .sku(variationDTO.getSku())
                .name(variationDTO.getName())
                .product(variationDTO.getProductId() != null ? productService.getOne(variationDTO.getProductId()) : null)
                .price(variationDTO.getPrice())
                .primaryImageUrl(variationDTO.getPrimaryImageUrl())
                .active(variationDTO.isActive())
                .available(variationDTO.getAvailable())
                .build();
    }

    public static VariationDTO entityToDTO(Variation variation){
        return VariationDTO
                .builder()
                .id(variation.getId())
                .slug(variation.getSlug())
                .sku(variation.getSku())
                .name(variation.getName())
                .productId(variation.getProduct() != null ? variation.getProduct().getId() : null)
                .primaryImageUrl(variation.getPrimaryImageUrl())
                .attributeValues(AttributeValueConverter.listToDTOList(variation.getAttributeValues().stream().map(attbValue -> attbValue.getAttributeValue()).collect(Collectors.toList())))
                .dateCreated(variation.getDateCreated())
                .active(variation.isActive())
                .price(variation.getPrice())
                .available(variation.getAvailable())
                .build();
    }

    public static List<VariationDTO> listToDTOList(List<Variation> variations) {
        return variations
                .stream()
                .map(variation -> entityToDTO(variation))
                .collect(Collectors.toList());
    }

}
