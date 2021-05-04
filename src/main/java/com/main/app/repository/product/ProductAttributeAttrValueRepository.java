package com.main.app.repository.product;

import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeAttrValueRepository extends JpaRepository<ProductAttributeAttrValueDTO, Long> {

    @Query(value =  "SELECT " +
                    "    av.id AS id, pav.product_id AS product_id, pav.attribute_value_id AS attribute_value_id, av.attribute_id AS attribute_id, av.name as attribute_value_name " +
                    "FROM " +
                    "    product_attribute_values AS pav, " +
                    "    attribute_value AS av " +
                    "WHERE " +
                    "    pav.attribute_value_id = av.id " +
                    "    AND pav.product_id = ?1", nativeQuery = true)
    List<ProductAttributeAttrValueDTO> findAllAttributeValuesForProductId(Long productId);

}
