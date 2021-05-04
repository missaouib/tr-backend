package com.main.app.domain.model.image;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.variation.Variation;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Image extends AbstractEntity {

    @NotBlank
    private String name;

    private String url;

    private boolean primaryImage;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Variation variation;
}
