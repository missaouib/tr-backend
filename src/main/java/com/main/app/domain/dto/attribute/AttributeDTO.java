package com.main.app.domain.dto.attribute;

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
public class AttributeDTO {

    private Long id;

    private String name;

    private boolean participatesInVariation;

    private boolean enteredManually;

    private List<Long> attributesId;

    private Instant dateCreated;


}
