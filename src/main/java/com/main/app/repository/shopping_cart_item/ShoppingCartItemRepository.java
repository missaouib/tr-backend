package com.main.app.repository.shopping_cart_item;

import com.main.app.domain.model.shopping_cart_item.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    Optional<ShoppingCartItem> findById(Long id);

    ShoppingCartItem findByVariationIdAndShoppingCartId(Long variationId, Long shoppingCartId);

    ShoppingCartItem findByProductIdAndShoppingCartId(Long productId, Long shoppingCartId);

    Long countByShoppingCartId(Long shoppingCartId);

}
