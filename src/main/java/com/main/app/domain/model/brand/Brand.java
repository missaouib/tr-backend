package com.main.app.domain.model.brand;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

/**
 * The Brand entity representing all brand values of the system.
 *
 * @author Nikola
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Brand extends AbstractEntity {

    @NotBlank
    private String name;

    private String description;

    private String primaryImageUrl;

    private String homepageImageUrl;

}
