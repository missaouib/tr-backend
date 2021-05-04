package com.main.app.elastic.dto.product;

import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.elastic.dto.EntityElasticDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "product")
public class ProductElasticDTO extends EntityElasticDTO {

    @Field(type = FieldType.Text, fielddata = true)
    private String name;

    @Field(type = FieldType.Text, fielddata = true)
    private String productCategoryId;

    @Field(type = FieldType.Text, fielddata = true)
    private String productDescription;

    @Field(type = FieldType.Text, fielddata = true)
    private String brandName;

    @Field(type = FieldType.Text, fielddata = true)
    private String categoryName;

    @Field(type = FieldType.Nested)
    private List<ProductAttributeAttrValueDTO> attributeValues;

    @Field(type = FieldType.Text, fielddata = true)
    private String slug;

    @Field(type = FieldType.Text, fielddata = true)
    private String sku;

    private boolean active;

    private Date dateCreated;

    private Long productPosition;
    private Long discountProductPosition;

    public ProductElasticDTO(Product product) {
        super(product.getId());
        this.name = product.getName();
        this.slug = product.getSlug();
        this.sku = product.getSku();
        this.productDescription = product.getDescription();
        this.productCategoryId = product.getProductCategory() != null ? String.valueOf(product.getProductCategory().getId()) : null;
        this.brandName = product.getBrand() != null ? product.getBrand().getName() : null;
        this.categoryName = product.getProductCategory() != null ? product.getProductCategory().getName() : null;
        this.active = product.isActive();
        this.dateCreated = Date.from(product.getDateCreated());
        this.productPosition = product.getProductPosition();
        this.discountProductPosition = product.getDiscountProductPosition();
    }

}
