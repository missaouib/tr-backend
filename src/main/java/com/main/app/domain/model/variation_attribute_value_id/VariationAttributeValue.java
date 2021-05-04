package com.main.app.domain.model.variation_attribute_value_id;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.variation.Variation;
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
@Table(name = "variation_attribute_values")
public class VariationAttributeValue extends AbstractEntity {

    @ManyToOne
    private Variation variation;

    @ManyToOne
    private Attribute attribute;

    @ManyToOne
    private AttributeValue attributeValue;

}
