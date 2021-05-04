package com.main.app.domain.model.shopping_cart;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import com.main.app.domain.model.user.User;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class ShoppingCart extends AbstractEntity {

    @ManyToOne
    private User user;

    private boolean status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shoppingCart")
    private List<ShoppingCartItem> items = new ArrayList<>();

}
