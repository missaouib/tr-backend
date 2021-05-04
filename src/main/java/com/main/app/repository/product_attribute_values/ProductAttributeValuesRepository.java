package com.main.app.repository.product_attribute_values;

import com.main.app.domain.model.product_attribute_values.ProductAttributeValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeValuesRepository extends JpaRepository<ProductAttributeValues, Long> {

    ProductAttributeValues save(ProductAttributeValues productAttributeValues);

    List<ProductAttributeValues> findAllByAttributeValueIdAndDeletedFalse(Long attributeValueId);

    List<ProductAttributeValues> findAllByProductId(Long id);
}
