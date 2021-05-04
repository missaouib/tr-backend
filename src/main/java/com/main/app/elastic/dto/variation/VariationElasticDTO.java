package com.main.app.elastic.dto.variation;

import com.main.app.domain.model.variation.Variation;
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
@Document(indexName = "variation")
public class VariationElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text, fielddata = true)
    private String slug;

    @Field(type = FieldType.Text, fielddata = true)
    private String sku;


    private Long productId;

    private Date dateCreated;

    public VariationElasticDTO(Variation variation) {
        super(variation.getId());
        this.name = variation.getName();
        this.slug = variation.getSlug();
        this.sku = variation.getSku();
        this.productId = variation.getProduct() != null ? variation.getProduct().getId() : null;
        this.dateCreated = Date.from(variation.getDateCreated());
    }

}
