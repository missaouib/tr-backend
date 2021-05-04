package com.main.app.converter.product;

import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.service.brand.BrandService;
import com.main.app.service.category.CategoryService;
import com.main.app.service.variation.VariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private static BrandService brandService;

    private static VariationService variationService;

    private static CategoryService categoryService;

    @Autowired
    private ProductConverter(
            BrandService brandService,
            VariationService variationService,
            CategoryService productCategoryService) {
            ProductConverter.variationService = variationService;
            ProductConverter.brandService = brandService;
            ProductConverter.categoryService = productCategoryService;
    }

    public static Product DTOtoEntity(ProductDTO productDTO){
        return Product
                .builder()
                .name(productDTO.getName())
                .slug(productDTO.getSlug())
                .sku(productDTO.getSku())
                .description(productDTO.getDescription())
                .productCategory(productDTO.getProductCategoryId() != null ? categoryService.getOne(productDTO.getProductCategoryId()) : null)
                .brand(productDTO.getBrandId() != null ? brandService.getOne(productDTO.getBrandId()) : null)
                .primaryImageUrl(productDTO.getPrimaryImageUrl())
                .price(productDTO.getPrice())
                .active(productDTO.isActive())
                .onSale(productDTO.isOnSale())
                .newAdded(productDTO.isNewAdded())
                .productPosition(productDTO.getProductPosition())
                .discount(productDTO.getDiscount())
                .discountProductPosition(productDTO.getDiscountProductPosition())
                .vremeIsporuke(productDTO.getVremeIsporuke())
                .available(productDTO.getAvailable())
                .selfTransport(productDTO.isSelfTransport())
                .suggestedProductIdSlot1(productDTO.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(productDTO.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(productDTO.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(productDTO.getSuggestedProductIdSlot4())
                .build();
    }

    public static ProductDTO entityToDTO(Product product){
        return ProductDTO
                .builder()
                .id(product.getId())
                .slug(product.getSlug())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .productCategoryId(product.getProductCategory() != null ? product.getProductCategory().getId() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandPrimaryImageUrl(product.getBrand() != null ? product.getBrand().getPrimaryImageUrl() : null)
                .primaryImageUrl(product.getPrimaryImageUrl())
                .price(product.getPrice())
                .active(product.isActive())
                .onSale(product.isOnSale())
                .newAdded(product.isNewAdded())
                .dateCreated(product.getDateCreated())
                .productPosition(product.getProductPosition())
                .discount(product.getDiscount())
                .discountProductPosition(product.getDiscountProductPosition())
                .brandName(product.getBrand() != null ? product.getBrand().getName()  : null )
                .categoryName(product.getProductCategory().getName())
                .vremeIsporuke(product.getVremeIsporuke())
                .available(product.getAvailable())
                .selfTransport(product.isSelfTransport())
                .variationCount(variationService.findAllForProductId(product.getId())  != null ? variationService.findAllForProductId(product.getId()).getTotal() : null)
                .suggestedProductIdSlot1(product.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(product.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(product.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(product.getSuggestedProductIdSlot4())
                .build();
    }

    public static ProductDTO entityToSafeDTO(Product product){
        return ProductDTO
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .productCategoryId(product.getProductCategory() != null ? product.getProductCategory().getId() : null)
                .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                .brandPrimaryImageUrl(product.getBrand() != null ? product.getBrand().getPrimaryImageUrl() : null)
                .primaryImageUrl(product.getPrimaryImageUrl())
                .price(product.getPrice())
                .active(product.isActive())
                .onSale(product.isOnSale())
                .newAdded(product.isNewAdded())
                .dateCreated(product.getDateCreated())
                .discount(product.getDiscount())
                .discountProductPosition(product.getDiscountProductPosition())
                .vremeIsporuke(product.getVremeIsporuke())
                .sku(product.getSku())
                .slug(product.getSlug())
                .available(product.getAvailable())
                .selfTransport(product.isSelfTransport())
                .suggestedProductIdSlot1(product.getSuggestedProductIdSlot1())
                .suggestedProductIdSlot2(product.getSuggestedProductIdSlot2())
                .suggestedProductIdSlot3(product.getSuggestedProductIdSlot3())
                .suggestedProductIdSlot4(product.getSuggestedProductIdSlot4())
                .build();
    }

    public static List<ProductDTO> listToDTOList(List<Product> products) {
        return products
                .stream()
                .map(product -> entityToDTO(product))
                .collect(Collectors.toList());
    }

}
