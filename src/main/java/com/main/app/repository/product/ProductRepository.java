package com.main.app.repository.product;

import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.variation.Variation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findOneByName(String name);

    Optional<Product> findBySlug(String slug);

    Optional<Product> findBySku(String sku);

    int countByName(String name);

    Page<Product> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<Product> findOneByNameAndDeletedFalse(String name);

    List<Product> findAllByNameAndDeletedFalse(String name);

    Optional<Product> findOneById(Long id);

    List<Product> findAllByBrandId(Long Id);

    List<Product> findAllByProductCategoryId(Long id);

    Optional<Product> findOneByProductPosition(Long id);

    Optional<Product> findOneByDiscountProductPosition(Long id);

}
