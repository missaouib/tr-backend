package com.main.app.domain.model.attribute_value;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute.Attribute;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class AttributeValue extends AbstractEntity {

    @NotBlank
    private String name;

    @OneToOne
    private Attribute attribute;

}
