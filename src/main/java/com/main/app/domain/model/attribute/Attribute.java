package com.main.app.domain.model.attribute;

import com.main.app.domain.model.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Attribute extends AbstractEntity {

    @NotBlank
    private String name;

    private boolean participatesInVariation;

    private boolean enteredManually;


}
