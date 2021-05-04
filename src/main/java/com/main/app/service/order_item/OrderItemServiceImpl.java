package com.main.app.service.order_item;

import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.repository.order.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.main.app.static_data.Messages.ORDER_ITEM_NOT_EXIST;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem create(ShoppingCartItem shoppingCartItem) {
        OrderItem orderItem = new OrderItem();
        if(shoppingCartItem.getVariation() !=  null){
            orderItem.setVariation(shoppingCartItem.getVariation());
        }else{
            orderItem.setVariation(null);
        }
        if(shoppingCartItem.getProduct() != null){
            orderItem.setProduct(shoppingCartItem.getProduct());
        }else{
            orderItem.setProduct(null);
        }
        orderItem.setQuantity(shoppingCartItem.getQuantity());
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ORDER_ITEM_NOT_EXIST));
    }

    @Override
    public OrderItem removeItemById(Long id) {
        OrderItem orderItem = findById(id);
        orderItem.setDeleted(true);
        return orderItemRepository.save(orderItem);
    }
}
