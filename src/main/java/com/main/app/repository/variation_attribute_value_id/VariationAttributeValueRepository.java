package com.main.app.repository.variation_attribute_value_id;


import com.main.app.domain.model.variation_attribute_value_id.VariationAttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VariationAttributeValueRepository extends JpaRepository<VariationAttributeValue, Long> {

    VariationAttributeValue save(VariationAttributeValue variationAttributeValue);

    List<VariationAttributeValue> findAllByVariationIdAndDeletedFalse(Long variationId);

}
