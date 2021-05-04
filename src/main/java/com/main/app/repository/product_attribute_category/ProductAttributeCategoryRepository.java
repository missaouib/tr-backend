package com.main.app.repository.product_attribute_category;

import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeCategoryRepository extends JpaRepository<ProductAttributeCategory, Long> {

    List<ProductAttributeCategory> findAllByProductId(Long product_id);
    List<ProductAttributeCategory> findByName(String name);
    List<ProductAttributeCategory> findByAttributeCategoryId(Long id);

    List<ProductAttributeCategory> findByAttributeValueId(Long id);
}
