package com.main.app.domain.dto.product_attribute_category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAttributeCategoryDTO {

    private Long id;

    private String name;

    private Long attributeId;

    private String attributeName;

    private Long attributeValueId;

    private String attributeValueName;


}
