package com.main.app.domain.dto.order_item;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long id;

    private String name;

    private String price;

    private Long order_id;

    private Long variation_id;

    private Long productId;

    private String sku;

    private int quantity;

    private String imageUrl;

    private String vremeIsporuke;

}
