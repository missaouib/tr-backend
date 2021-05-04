package com.main.app.domain.dto.product_prominent_attributes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributesDTO {

    private Long id;

    private Long attributeId;

    private String attributeName;

    private Long attributeValueId;

    private String attributeValueName;

    private Long productId;

    private boolean prominent;

}
