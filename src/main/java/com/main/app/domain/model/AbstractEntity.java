package com.main.app.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract class extended by all entities that
 * need to have information provided by this class.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant dateCreated;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Instant dateUpdated;

    private Instant dateDeleted;

    private boolean deleted = false;
}
