package com.main.app.service.shopping_cart;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public interface ShoppingCartService {

    public ShoppingCart findShoppingCartById(Long id);

    public ShoppingCart removeShoppingCartItem(Long id, Long itemId);

    public ShoppingCart changeShoppingCartItemQuantity(Long id, Long itemId, ShoppingCartItemDto shoppingCartItemDto);

    public ShoppingCart createShoppingCart();

    public ShoppingCart addItemToShoppingCart(Long id, ShoppingCartItemDto shoppingCartItemDto);

    public Long getShoppingCartSize(Long id);

    public ShoppingCart findShoppingCartByUser();

    public ShoppingCart connectShoppingCartToUser(Long id);

    public void removeAndClearShoppingCart(Long id);

}
