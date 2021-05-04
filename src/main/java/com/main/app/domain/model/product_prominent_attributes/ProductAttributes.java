package com.main.app.domain.model.product_prominent_attributes;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.product.Product;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Embeddable
@Where(clause = "deleted = false")
@Table(name = "product_attributes")
public class ProductAttributes extends AbstractEntity {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Attribute attribute;

    @ManyToOne
    private AttributeValue attributeValue;

    private boolean prominent;
}
