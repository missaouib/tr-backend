package com.main.app.elastic.dto.attribute_category;

import com.main.app.domain.dto.attribute_category.AttributeCategoryUniqueDTO;
import com.main.app.domain.model.attribute_category.AttributeCategory;
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
@Document(indexName = "attribute_category_unique")
public class AttributeCategoryUniqueElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    private Date dateCreated;

    public AttributeCategoryUniqueElasticDTO(AttributeCategoryUniqueDTO attributeCategory) {
        super(attributeCategory.getId());
        this.name = attributeCategory.getName();
        this.dateCreated = Date.from(attributeCategory.getDateCreated());
    }

}
