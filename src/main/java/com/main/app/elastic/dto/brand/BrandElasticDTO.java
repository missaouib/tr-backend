package com.main.app.elastic.dto.brand;

import com.main.app.domain.model.brand.Brand;
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
@Document(indexName = "brand")
public class BrandElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    private Date dateCreated;

    public BrandElasticDTO(Brand brand) {
        super(brand.getId());
        this.name = brand.getName();
        this.dateCreated = Date.from(brand.getDateCreated());
    }

}
