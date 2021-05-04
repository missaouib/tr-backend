package com.main.app.service.shopping_cart_item;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartItemService {

    ShoppingCartItem findById(Long id);

    ShoppingCartItem removeItemById(Long id);

    ShoppingCartItem changeItemQuantity(Long id, ShoppingCartItemDto dto);

    ShoppingCartItem create(ShoppingCartItemDto shoppingCartItemDto);

    ShoppingCartItem findByVariationAndShoppingCart(Long variationId, Long shoppingCartId);

    ShoppingCartItem findByProductAndShoppingCart(Long productId, Long shoppingCartId);

}
