package com.main.app.domain.model.category;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

/**
 * The ProductCategory entity representing all product categories of the system.
 *
 * @author Stefan
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Category extends AbstractEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String title;

    @NotBlank
    private String subtitle;

    @NotBlank
    private String contentText;

    @NotBlank
    private String description;


    @ManyToOne
    @JoinColumn(name="parent_category_id")
    private Category parentCategory;

    private String primaryImageUrl;

}
