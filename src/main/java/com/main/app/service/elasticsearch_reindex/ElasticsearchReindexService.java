package com.main.app.service.elasticsearch_reindex;
import com.main.app.domain.dto.attribute_category.AttributeCategoryUniqueDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.brand.Brand;
import com.main.app.domain.model.category.Category;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.user.User;
import com.main.app.domain.model.variation.Variation;
import com.main.app.elastic.dto.attribute.AttributeElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryUniqueElasticDTO;
import com.main.app.elastic.dto.attribute_value.AttributeValueElasticDTO;
import com.main.app.elastic.dto.brand.BrandElasticDTO;
import com.main.app.elastic.dto.category.CategoryElasticDTO;
import com.main.app.elastic.dto.order.OrdersElasticDTO;
import com.main.app.elastic.dto.product.ProductElasticDTO;
import com.main.app.elastic.dto.user.UserElasticDTO;
import com.main.app.elastic.dto.variation.VariationElasticDTO;
import com.main.app.elastic.repository.attribute.AttributeElasticRepository;
import com.main.app.elastic.repository.attribute_category.AttributeCategoryElasticRepository;
import com.main.app.elastic.repository.attribute_category_unique.AttributeCategoryUniqueElasticRepository;
import com.main.app.elastic.repository.attribute_category_unique.AttributeCategoryUniqueElasticRepositoryBuilder;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepository;
import com.main.app.elastic.repository.brand.BrandElasticRepository;
import com.main.app.elastic.repository.category.CategoryElasticRepository;
import com.main.app.elastic.repository.order.OrderElasticRepository;
import com.main.app.elastic.repository.product.ProductElasticRepository;
import com.main.app.elastic.repository.user.UserElasticRepository;
import com.main.app.elastic.repository.variation.VariationElasticRepository;
import com.main.app.repository.attribute.AttributeRepository;
import com.main.app.repository.attribute_category.AttributeCategoryRepository;
import com.main.app.repository.attribute_value.AttributeValueRepository;
import com.main.app.repository.brand.BrandRepository;
import com.main.app.repository.category.CategoryRepository;
import com.main.app.repository.order.OrderRepository;
import com.main.app.repository.product.ProductRepository;
import com.main.app.repository.user.UserRepository;
import com.main.app.repository.variation.VariationRepository;
import com.main.app.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ElasticsearchReindexService {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchReindexService.class);

    private final UserRepository userRepository;

    private final UserElasticRepository userElasticRepository;

    private final CategoryRepository categoryRepository;

    private final CategoryElasticRepository categoryElasticRepository;

    private final AttributeRepository attributeRepository;

    private final AttributeElasticRepository attributeElasticRepository;

    private final AttributeValueRepository attributeValueRepository;

    private final AttributeValueElasticRepository attributeValueElasticRepository;

    private final AttributeCategoryRepository attributeCategoryRepository;

    private final AttributeCategoryElasticRepository attributeCategoryElasticRepository;

    private final BrandRepository brandRepository;

    private final BrandElasticRepository brandElasticRepository;

    private final ProductService productService;

    private final ProductRepository productRepository;

    private final ProductElasticRepository productElasticRepository;

    private final VariationRepository variationRepository;

    private final VariationElasticRepository variationElasticRepository;

    private final OrderRepository orderRepository;

    private final OrderElasticRepository orderElasticRepository;

    private final AttributeCategoryUniqueElasticRepository attributeCategoryUniqueElasticRepository;

    private final AttributeCategoryUniqueElasticRepositoryBuilder attributeCategoryUniqueElasticRepositoryBuilder;


    public void reindexAll(){
        reindexUser();
        reindexCategory();
        reindexAttribute();
        reindexAttributeValue();
        reindexBrand();
        reindexProduct();
        reindexVariation();
        reindexOrders();
        reindexAttributeCategory();
        logger.info("[RE-INDEX]Successfully re-indexed ALL");
    }

    public void reindexUser(){
        List<User> usersList = userRepository.findAllByDeletedFalse();
        usersList.forEach(user -> {
            userElasticRepository.save(new UserElasticDTO(user));
        });
        logger.info("[RE-INDEX]Successfully re-indexed USER");
    }

    public void reindexCategory(){
        List<Category> productCategoriesList = categoryRepository.findAll();
        productCategoriesList.forEach(productCategory -> {
            categoryElasticRepository.save(new CategoryElasticDTO(productCategory));
        });
        logger.info("[RE-INDEX]Successfully re-indexed CATEGORY");
    }

    public void reindexAttribute(){
        List<Attribute> attributeList = attributeRepository.findAll();
        attributeList.forEach(attribute -> {
            attributeElasticRepository.save(new AttributeElasticDTO(attribute));
        });
        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE");
    }

    public void reindexAttributeValue(){
        List<AttributeValue> attributeValueList = attributeValueRepository.findAll();
        attributeValueList.forEach(attributeValue -> {
            attributeValueElasticRepository.save(new AttributeValueElasticDTO(attributeValue));
        });
        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE VALUE");
    }

    public void reindexBrand(){
        List<Brand> brandList = brandRepository.findAll();
        brandList.forEach(brand -> {
            brandElasticRepository.save(new BrandElasticDTO(brand));
        });
        logger.info("[RE-INDEX]Successfully re-indexed BRAND");
    }

    public void reindexProduct(){
        List<Product> productList = productRepository.findAll();
        productList.forEach(product -> {
            ProductElasticDTO productElasticDTO = new ProductElasticDTO(product);
            productElasticDTO.setAttributeValues(productService.getAllAttributeValsForProductId(product.getId()));
            productElasticRepository.save(productElasticDTO);
        });
        logger.info("[RE-INDEX]Successfully re-indexed PRODUCT");
    }

    public void reindexVariation(){
        List<Variation> variationList = variationRepository.findAll();
        variationList.forEach(variation -> {
            variationElasticRepository.save(new VariationElasticDTO(variation));
        });
        logger.info("[RE-INDEX]Successfully re-indexed VARIATION");
    }

    public void reindexOrders() {
        List<CustomerOrder> ordersList = orderRepository.findAll();
        ordersList.forEach(order -> {
            orderElasticRepository.save(new OrdersElasticDTO(order));
        });
        logger.info("[RE-INDEX]Successfully re-indexed ORDERS");
    }

    public void reindexAttributeCategory() {
        List<AttributeCategory> attributeCategories = attributeCategoryRepository.findAll();
        attributeCategories.forEach(attributeCategory -> {
            attributeCategoryElasticRepository.save(new AttributeCategoryElasticDTO(attributeCategory));
        });
//        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE CATEGORY");


        ArrayList<AttributeCategoryUniqueDTO> uniqueNames = new ArrayList<>();
        for (AttributeCategory category: attributeCategories) {
            int brojac=0;
            for (AttributeCategoryUniqueDTO unique: uniqueNames) {
                if(unique.getName().equals(category.getName())){
                    brojac++;
                }
            }
            if(brojac == 0){
                uniqueNames.add(new AttributeCategoryUniqueDTO(category.getId(),category.getName(),category.getDateCreated()));

            }

        }

        uniqueNames.forEach(unique -> {
            attributeCategoryUniqueElasticRepository.save(new AttributeCategoryUniqueElasticDTO(unique));

        });
        logger.info("[RE-INDEX]Successfully re-indexed ATTRIBUTE CATEGORY");
    }
}
