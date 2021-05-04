package com.main.app.domain.dto.variation;

import com.main.app.domain.dto.attribute_value.AttributeValueDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariationDTO {

    private Long id;

    private String name;

    private String slug;

    private String sku;

    private Long productId;

    private Double price;

    private Integer available;

    private boolean active;

    private String primaryImageUrl;

    private Instant dateCreated;

    private List<AttributeValueDTO> attributeValues;

}
