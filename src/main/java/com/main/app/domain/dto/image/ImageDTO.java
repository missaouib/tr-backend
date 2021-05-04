package com.main.app.domain.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDTO {

    private Long id;

    private String name;

    private String url;

    private boolean primaryImage;

    private Long productId;

    private Long variationId;

    private Instant dateCreated;

}
