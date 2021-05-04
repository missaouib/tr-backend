package com.main.app.service.order_item;

import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import org.springframework.stereotype.Service;

@Service
public interface OrderItemService {

    public OrderItem create(ShoppingCartItem shoppingCartItem);

    OrderItem findById(Long id);

    public  OrderItem removeItemById(Long id);

}
