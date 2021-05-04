package com.main.app.converter.shopping_cart_item;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;

import java.util.List;
import java.util.stream.Collectors;
import static com.main.app.converter.product.ProductConverter.entityToDTO;
import static com.main.app.converter.variation.VariationConverter.entityToDTO;

public class ShoppingCartItemConverter {

    public static ShoppingCartItemDto toDto(ShoppingCartItem shoppingCartItem) {
        return ShoppingCartItemDto.builder()
                .id(shoppingCartItem.getId())
                .quantity(shoppingCartItem.getQuantity())
                .variationDTO(shoppingCartItem.getVariation() != null ? entityToDTO(shoppingCartItem.getVariation()) : null)
                .productDTO(shoppingCartItem.getProduct() != null ? entityToDTO(shoppingCartItem.getProduct()) : null)
                .productName(shoppingCartItem.getVariation() != null ? shoppingCartItem.getVariation().getProduct().getName() : (shoppingCartItem.getProduct() != null ? shoppingCartItem.getProduct().getName() : null))
                .build();
    }

    public static List<ShoppingCartItemDto> toDtoList(List<ShoppingCartItem> shoppingCartItems) {
        return shoppingCartItems
                .stream()
                .map(ShoppingCartItemConverter::toDto)
                .collect(Collectors.toList());
    }


}
