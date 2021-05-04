package com.main.app.converter.shopping_cart;

import com.main.app.domain.dto.shopping_cart.ShoppingCartDto;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import org.springframework.stereotype.Component;

import static com.main.app.converter.shopping_cart_item.ShoppingCartItemConverter.toDtoList;

@Component
public class ShoppingCartConverter {

    public static ShoppingCartDto toDto(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
                .id(shoppingCart.getId())
                .items(toDtoList(shoppingCart.getItems()))
                .size(shoppingCart.getItems().size())
                .build();

    }
}
