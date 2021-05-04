package com.main.app.repository.shopping_cart;

import com.main.app.domain.model.shopping_cart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findById(Long id);
    Optional<ShoppingCart> findByUserId(Long userId);
    Optional<List<ShoppingCart>> findAllByUserIdOrderByDateCreatedDesc(Long userId);

}
