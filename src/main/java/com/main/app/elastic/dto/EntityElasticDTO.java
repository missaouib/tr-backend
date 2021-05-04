package com.main.app.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
public class EntityElasticDTO {

    @Id
    private Long id;

    private boolean deleted = false;

    public EntityElasticDTO(Long id) {
        this.id = id;
    }
}
