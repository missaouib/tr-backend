package com.main.app.domain.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {

    private Long id;

    private String name;

    private String title;

    private String subtitle;

    private String contentText;

    private String description;

    private Long parentProductCategoryId;

    private String parentProductCategoryName;

    private String primaryImageUrl;

    private Instant dateCreated;

}
