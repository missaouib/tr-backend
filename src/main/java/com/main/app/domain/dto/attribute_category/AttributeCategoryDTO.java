package com.main.app.domain.dto.attribute_category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeCategoryDTO {

    private Long id;

    private String name;

    private String oldName;

    private String attributeName;

    private Long attributeId;

    private Instant dateCreated;

    private List<Long> attributeIds;

    private boolean enteredManually;
}
