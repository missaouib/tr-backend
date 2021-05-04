package com.main.app.domain.dto.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The dto used for exposing brand data through API.
 *
 * @author Nikola
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandDTO {

    private Long id;

    private String name;

    private String description;

    private String primaryImageUrl;

    private String homepageImageUrl;

    private Instant dateCreated;

}
