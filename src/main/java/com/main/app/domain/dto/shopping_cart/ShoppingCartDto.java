package com.main.app.domain.dto.shopping_cart;

import com.main.app.domain.dto.shopping_cart_item.ShoppingCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCartDto {

    private Long id;

    private List<ShoppingCartItemDto> items;

    private Integer size;

}
