package com.main.app.converter.order;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order_item.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemConverter {

    public static OrderItemDto toDtoItems(OrderItem item) {
        return OrderItemDto.builder()
                .id(item.getId())
                .quantity(item.getQuantity())
                .variation_id(item.getVariation() != null ? item.getVariation().getId() : null)
                .productId(item.getProduct() != null ? item.getProduct().getId() : null)
                .order_id(item.getCustomerOrder().getId())
                .name(item.getVariation() != null? item.getVariation().getName() : (item.getProduct()!=null ? item.getProduct().getName() : null ) )
                .price(item.getVariation() != null? item.getVariation().getPrice().toString() : (item.getProduct() != null ? item.getProduct().getPrice().toString()  : null))
                .imageUrl(item.getVariation() != null? item.getVariation().getPrimaryImageUrl() : (item.getProduct() != null ? item.getProduct().getPrimaryImageUrl() : null))
                .sku(item.getVariation() != null? item.getVariation().getSku() : (item.getProduct() != null? item.getProduct().getSku() : null))
                .vremeIsporuke(item.getVariation() != null ? item.getVariation().getProduct().getVremeIsporuke() : (item.getProduct() != null? item.getProduct().getVremeIsporuke() : null))
                .build();
    }

    public static List<OrderItemDto> toDtoListItems(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(OrderItemConverter::toDtoItems)
                .collect(Collectors.toList());
    }


}
