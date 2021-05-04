package com.main.app.domain.dto.attribute_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeCategoryUniqueDTO {


    private Long id;

    private String name;

    private Instant dateCreated;
}
