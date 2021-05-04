package com.main.app.domain.dto.attribute_value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeValueDTO {

    private Long id;

    private String name;

    private Long attributeId;

    private String attributeName;

    private Instant dateCreated;

}
