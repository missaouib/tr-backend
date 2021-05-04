package com.main.app.domain.model.shopping_cart_item;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.shopping_cart.ShoppingCart;
import com.main.app.domain.model.variation.Variation;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class ShoppingCartItem extends AbstractEntity {

    @ManyToOne
    private ShoppingCart shoppingCart;

    private int quantity;

    @ManyToOne
    private Variation variation;

    @ManyToOne
    private Product product;

}
