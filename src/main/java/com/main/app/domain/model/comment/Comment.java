package com.main.app.domain.model.comment;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.variation.Variation;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Comment extends AbstractEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Variation variation;

    @Column( nullable = false )
    private String commentDescription;

    private Boolean verified;
}
