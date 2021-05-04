package com.main.app.service.product;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.dto.product.ProductAttributeValueDTO;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;
import com.main.app.domain.model.product_prominent_attributes.ProductAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

//    Product changeSlugForProductId(Long id,String slug);

    String buildSlug(String title,int numberOfRepeat);

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Long productType, Pageable pageable);

    Product getOne(Long id);

    Product save(ProductDTO productDTO, Product product);

    Product edit(Product product, Long id);

    Product delete(Long id);

    void uploadImage(Long id, MultipartFile[] images) throws IOException;

    Product toggleActivate(Long id);

    void checkIfHasForeignKey(Long id, String entity);

    List<ProductAttributeValueDTO> getAllAttributeValuesForProductId(Long productId);

    List<ProductAttributeAttrValueDTO> getAllAttributeValsForProductId(Long productId);

    List<ProductAttributeCategory> getAllAttributeCategoryForProduct(Long id);

    List<ProductAttributes> findForProductId(Long product_id);

    List<ProductDTO> getAllSuggestedProducts(Long id);

    Product getOneBySlug(String productSlug);

    Integer getPossiblyAvailableForProductId(Long id);
}


