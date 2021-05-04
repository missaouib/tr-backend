package com.main.app.repository.product_prominent_attributes;

import com.main.app.domain.model.product_prominent_attributes.ProductAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, Long> {

    List<ProductAttributes> findAllByAttributeIdAndDeletedFalse(Long attributeId);

    List<ProductAttributes> findAllByProductIdAndDeletedFalse(Long productId);

}
