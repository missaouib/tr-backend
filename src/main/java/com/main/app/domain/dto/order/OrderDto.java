package com.main.app.domain.dto.order;

import com.main.app.domain.dto.order_item.OrderItemDto;
import com.main.app.domain.model.order_item.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private Instant dateCreated;

    private String postalCode;
    private Integer deliveryMethod;
    private Integer paymentMethod;

    private String note;
    private String name;
    private String surname;
    private String email;
    private String city;
    private String address;
    private String phoneNumber;

    private Long shoppingCartId;

    private String status;
    private Integer totalPrice;
    private Long user_id;

    private List<OrderItemDto> items;

    private String shop;

}
