package com.main.app.domain.model.counter_slug;


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
public class CounterSlug extends AbstractEntity {

    @NotBlank
    private String entityName;

    private int currentCount;

}
