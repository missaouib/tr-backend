package com.main.app.elastic.dto.attribute;

import com.main.app.domain.model.attribute.Attribute;
import com.main.app.elastic.dto.EntityElasticDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "attribute")
public class AttributeElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    private Date dateCreated;

    private boolean participatesInVariation;

    private boolean enteredManually;

    public AttributeElasticDTO(Attribute attribute) {
        super(attribute.getId());
        this.name = attribute.getName();
        this.dateCreated = Date.from(attribute.getDateCreated());
        this.participatesInVariation = attribute.isParticipatesInVariation();
        this.enteredManually = attribute.isEnteredManually();
    }

}
