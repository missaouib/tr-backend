package com.main.app.domain.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    private String sku;

    private String slug;

    private String name;

    private Long productCategoryId;

    private String description;

    private Long brandId;

    private String brandPrimaryImageUrl;

    private String primaryImageUrl;

    private Double price;

    private boolean active;

    private boolean newAdded;

    private boolean onSale;

    private boolean selfTransport;

    private String vremeIsporuke;

    private HashMap<Long, List<Long>> sunshineUseIds;

    private HashMap<String, String> attrCategoryContent;

    private HashMap<Long, List<Long>> attributeValueIds;

    private List<Long> prominentAttrIds;

    private Instant dateCreated;

    private Long discount;

    private Long productPosition;

    private Long discountProductPosition;

    private String brandName;

    private String categoryName;

    private Integer available;

    private Long variationCount;

    private Long suggestedProductIdSlot1;

    private Long suggestedProductIdSlot2;

    private Long suggestedProductIdSlot3;

    private Long suggestedProductIdSlot4;

}
