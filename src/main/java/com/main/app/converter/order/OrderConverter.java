package com.main.app.converter.order;

import com.main.app.domain.dto.order.OrderDto;
import com.main.app.domain.dto.user.UserDTO;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

import static com.main.app.converter.order.OrderItemConverter.toDtoListItems;

public class OrderConverter {

    public static CustomerOrder toEntity(OrderDto orderDto) {
        return CustomerOrder.builder()
                .buyerCity(orderDto.getCity())
                .buyerEmail(orderDto.getEmail())
                .buyerName(orderDto.getName())
                .buyerSurname(orderDto.getSurname())
                .buyerPhone(orderDto.getPhoneNumber())
                .buyerAddress(orderDto.getAddress())
                .cityPostalCode(orderDto.getPostalCode())
                .note(orderDto.getNote())
                .deliveryType(orderDto.getDeliveryMethod() == 1 ? "STORE" : "HOME ADDRESS")
                .paymentProcessType(orderDto.getPaymentMethod() == 1 ? "ON DELIVERY" : "ONLINE")
                .shop(orderDto.getShop())
                .build();
    }

    public static OrderDto toDto(CustomerOrder order) {
        return OrderDto.builder()
                .dateCreated(order.getDateCreated())
                .id(order.getId())
                .city(order.getBuyerCity())
                .email(order.getBuyerEmail())
                .name(order.getBuyerName())
                .surname(order.getBuyerSurname())
                .phoneNumber(order.getBuyerPhone())
                .address(order.getBuyerAddress())
                .postalCode(order.getCityPostalCode())
                .note(order.getNote())
                .deliveryMethod(order.getDeliveryType().equals("STORE") ? 1 : 2)
                .paymentMethod(order.getPaymentProcessType().equals("ON DELIVERY") ? 1 : 2)
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .items(toDtoListItems(order.getOrderItems()))
                .user_id(order.getUser() != null ? order.getUser().getId() : null)
                .shop(order.getShop())
                .build();
    }

    public static List<OrderDto> ordersListToOrdersDTOList(List<CustomerOrder> orders) {
        return orders
                .stream()
                .map(order -> toDto(order))
                .collect(Collectors.toList());
    }


}
