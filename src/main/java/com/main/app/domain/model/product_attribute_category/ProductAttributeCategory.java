package com.main.app.domain.model.product_attribute_category;


import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.product.Product;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class ProductAttributeCategory extends AbstractEntity {

    private String name;

    @ManyToOne
    private Product product;

    @ManyToOne
    private AttributeCategory attributeCategory;

    @ManyToOne
    private AttributeValue attributeValue;
}
