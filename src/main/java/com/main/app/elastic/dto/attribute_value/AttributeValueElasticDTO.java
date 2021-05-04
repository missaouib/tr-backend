package com.main.app.elastic.dto.attribute_value;

import com.main.app.domain.model.attribute_value.AttributeValue;
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
@Document(indexName = "attribute_value")
public class AttributeValueElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text, fielddata = true)
    private String attributeName;

    private Long attributeId;

    private Date dateCreated;

    public AttributeValueElasticDTO(AttributeValue attributeValue) {
        super(attributeValue.getId());
        this.name = attributeValue.getName();
        this.attributeId = attributeValue.getAttribute() != null ? attributeValue.getAttribute().getId() : null;
        this.attributeName = attributeValue.getAttribute() != null ? attributeValue.getAttribute().getName() : null;
        this.dateCreated = Date.from(attributeValue.getDateCreated());
    }

}
