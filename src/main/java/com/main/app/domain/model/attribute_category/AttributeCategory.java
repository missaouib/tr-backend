package com.main.app.domain.model.attribute_category;


import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute.Attribute;
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
public class AttributeCategory  extends AbstractEntity {

    @NotBlank
    private String name;

    private String attribute_name;

    @ManyToOne
    private Attribute attribute;

}
