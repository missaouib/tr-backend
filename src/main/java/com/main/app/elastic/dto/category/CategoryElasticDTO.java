package com.main.app.elastic.dto.category;


import com.main.app.domain.model.category.Category;
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
@Document(indexName = "category")
public class CategoryElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text, fielddata = true)
    private String parentProductCategoryName;

    private Date dateCreated;

    public CategoryElasticDTO(Category category) {
        super(category.getId());
        this.name = category.getName();
        this.parentProductCategoryName = category.getParentCategory() != null ? category.getParentCategory().getName() : null;
        this.dateCreated = Date.from(category.getDateCreated());
    }


}
