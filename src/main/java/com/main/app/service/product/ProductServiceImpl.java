package com.main.app.service.product;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.dto.product.ProductAttributeValueDTO;
import com.main.app.domain.dto.product.ProductDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.counter_slug.CounterSlug;
import com.main.app.domain.model.image.Image;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;
import com.main.app.domain.model.product_attribute_values.ProductAttributeValues;
import com.main.app.domain.model.product_prominent_attributes.ProductAttributes;
import com.main.app.domain.model.variation.Variation;
import com.main.app.elastic.dto.product.ProductElasticDTO;
import com.main.app.elastic.repository.product.ProductElasticRepository;
import com.main.app.elastic.repository.product.ProductElasticRepositoryBuilder;
import com.main.app.repository.attribute.AttributeRepository;
import com.main.app.repository.attribute_category.AttributeCategoryRepository;
import com.main.app.repository.attribute_value.AttributeValueRepository;
import com.main.app.repository.counter_slug.CounterSlugRepository;
import com.main.app.repository.image.ImageRepository;
import com.main.app.repository.product.ProductAttributeAttrValueRepository;
import com.main.app.repository.product.ProductRepository;
import com.main.app.repository.product_attribute_category.ProductAttributeCategoryRepository;
import com.main.app.repository.product_attribute_values.ProductAttributeValuesRepository;
import com.main.app.repository.product_prominent_attributes.ProductAttributesRepository;
import com.main.app.repository.variation.VariationRepository;
import com.main.app.util.ObjectMapperUtils;
import com.main.app.util.Slug;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.product.ProductConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.MD5HashUtil.md5;
import static com.main.app.util.Util.productsToIds;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

    @Value("${document.productcategory.path}")
    private String documentPath;

    private final ProductRepository productRepository;

    private final ProductElasticRepository productElasticRepository;

    private final ProductElasticRepositoryBuilder productElasticRepositoryBuilder;

    private final ImageRepository imageRepository;

    private final ProductAttributesRepository productAttributesRepository;

    private final ProductAttributeValuesRepository productAttributeValuesRepository;

    private final ProductAttributeAttrValueRepository productAttributeAttrValueRepository;

    private final VariationRepository variationRepository;

    private final CounterSlugRepository counterSlugRepository;

    private final AttributeCategoryRepository attributeCategoryRepository;

    private final AttributeRepository attributeRepository;


    private final AttributeValueRepository attributeValueRepository;

    private final ProductAttributeCategoryRepository productAttributeCategoryRepository;

    @Override
    public Entities getAll() {
        List<Product> productList = productRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(productList));
        entities.setTotal(productList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Long productType, Pageable pageable) {
        Page<ProductElasticDTO> pagedProducts = productElasticRepository.search(productElasticRepositoryBuilder.generateQuery(searchParam, productType), pageable);

        List<Long> ids = productsToIds(pagedProducts);

        Pageable mySqlPaging = null;
        if(productType == 2){
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), Sort.by("productPosition"));
        }else if(productType == 3){
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), Sort.by("discountProductPosition"));
        }else{
            mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        }

        List<Product> products = productRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(products));
        entities.setTotal(pagedProducts.getTotalElements());

        return entities;
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.getOne(id);
    }

    @Override
    public Product getOneBySlug(String productSlug) {
        return productRepository.findBySlug(productSlug).get();
    }

    @Override
    public Integer getPossiblyAvailableForProductId(Long id) {
        Product product = productRepository.getOne(id);
        List<Variation> variationList = variationRepository.findAllByProductId(product.getId());

        Integer varAvailableCount = 0;
        for (Variation variation: variationList) {
            if(variation.getAvailable() == null){
                continue;
            }
            varAvailableCount += variation.getAvailable();
        }
        return product.getAvailable() - varAvailableCount;
    }

    @Override
    public String buildSlug(String title,int numberOfRepeat) {
        return Slug.makeSlug(title+" "+numberOfRepeat);
    }

    @Override
    public Product save(ProductDTO productDTO, Product product) {

        if(product.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NAME_CANT_BE_NULL);
        }

        if(productRepository.countByName(product.getName()) == 0){
            counterSlugRepository.save(new CounterSlug(product.getName(),1));
        }else{
            CounterSlug entity = counterSlugRepository.findByEntityName(product.getName());
            entity.setCurrentCount(entity.getCurrentCount()+1);
            counterSlugRepository.save(entity);
        }

        if(product.getProductPosition() != null) {
            Optional<Product> sameProductPosition = productRepository.findOneByProductPosition(product.getProductPosition());
            if(sameProductPosition.isPresent()){
                Product foundedSameProductPosition = sameProductPosition.get();
                foundedSameProductPosition.setProductPosition(null);
                Product savedSameProductPosition = productRepository.save(foundedSameProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameProductPosition));
            }
        }
        if(product.getDiscountProductPosition() != null) {
            Optional<Product> sameDiscountProductPosition = productRepository.findOneByDiscountProductPosition(product.getDiscountProductPosition());
            if (sameDiscountProductPosition.isPresent()) {
                Product foundedSameDiscountProductPosition = sameDiscountProductPosition.get();
                foundedSameDiscountProductPosition.setDiscountProductPosition(null);
                Product savedSameDiscountProductPosition = productRepository.save(foundedSameDiscountProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameDiscountProductPosition));
            }
        }

        CounterSlug counterSlug = counterSlugRepository.findByEntityName(product.getName());
        int numberOfRepeat = counterSlug.getCurrentCount();

        String slug = buildSlug(product.getName(),numberOfRepeat);
        product.setSlug(slug);

        Product savedProduct = productRepository.save(product);
        productElasticRepository.save(new ProductElasticDTO(savedProduct));


        for (String attrKey: productDTO.getAttrCategoryContent().keySet()){

            Attribute tempAttr = attributeRepository.findOneByName(attrKey).get();
            AttributeCategory attributeCategory = attributeCategoryRepository.findOneByAttributeId(tempAttr.getId()).get();
            AttributeValue value = new AttributeValue();

            if(isNumeric(productDTO.getAttrCategoryContent().get(attrKey))){

                Long valueKey = Long.valueOf(productDTO.getAttrCategoryContent().get(attrKey));
                value = attributeValueRepository.findOneById(valueKey).get();

            }else{
                String valueString = String.valueOf(productDTO.getAttrCategoryContent().get(attrKey));
                Attribute attr = attributeRepository.findOneByName(attrKey).get();
                value = new AttributeValue(valueString,attr);
                attributeValueRepository.save(value);
            }

            for (Long  prominentId : productDTO.getProminentAttrIds()) {
                if(prominentId == tempAttr.getId()){
                    ProductAttributes productAttributes = new ProductAttributes();
                    productAttributes.setAttribute(tempAttr);
                    productAttributes.setProduct(product);
                    productAttributes.setAttributeValue(value);
                    productAttributes.setProminent(true);
                    productAttributesRepository.save(productAttributes);
                }
            }


            ProductAttributeCategory productAttributeCategory= new ProductAttributeCategory();
            productAttributeCategory.setName(attributeCategory.getName());
            productAttributeCategory.setProduct(product);
            productAttributeCategory.setAttributeCategory(attributeCategory);
            productAttributeCategory.setAttributeValue(value);
            ProductAttributeCategory savedProductAttributeCategory = productAttributeCategoryRepository.save(productAttributeCategory);
        }

        for (Long attrKey: productDTO.getSunshineUseIds().keySet()){
            for (Long attrValueKey : productDTO.getSunshineUseIds().get(attrKey)) {
                ProductAttributeValues productAttributeValues = new ProductAttributeValues();
                AttributeValue attrValue = attributeValueRepository.findById(attrValueKey).get();

                productAttributeValues.setProduct(savedProduct);
                productAttributeValues.setAttributeValue(attrValue);

                productAttributeValuesRepository.save(productAttributeValues);
            }
        }
        return savedProduct;
    }

    @Override
    public Product edit(Product product, Long id) {
//        Optional<Product> oneProduct = productRepository.findOneByNameAndDeletedFalse(product.getName());

        if(product.getProductPosition() != null) {
            Optional<Product> sameProductPosition = productRepository.findOneByProductPosition(product.getProductPosition());
            if (sameProductPosition.isPresent()) {
                Product foundedSameProductPosition = sameProductPosition.get();
                foundedSameProductPosition.setProductPosition(null);
                Product savedSameProductPosition = productRepository.save(foundedSameProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameProductPosition));
            }
        }
        if(product.getDiscountProductPosition() != null) {
            Optional<Product> sameDiscountProductPosition = productRepository.findOneByDiscountProductPosition(product.getDiscountProductPosition());
            if (sameDiscountProductPosition.isPresent()) {
                Product foundedSameDiscountProductPosition = sameDiscountProductPosition.get();
                foundedSameDiscountProductPosition.setDiscountProductPosition(null);
                Product savedSameDiscountProductPosition = productRepository.save(foundedSameDiscountProductPosition);
                productElasticRepository.save(new ProductElasticDTO(savedSameDiscountProductPosition));
            }
        }

//        if(oneProduct.isPresent() && !id.equals(oneProduct.get().getId())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_WITH_NAME_ALREADY_EXIST);
//        }

        Optional<Product> optionalProduct = productRepository.findOneById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();

        if(product.getName() != null){
            foundProduct.setName(product.getName());
        }
        if(product.getAvailable() != null){
            foundProduct.setAvailable(product.getAvailable());
        }
        if(product.getVremeIsporuke() != null){
            foundProduct.setVremeIsporuke(product.getVremeIsporuke());
        }

        foundProduct.setSelfTransport(product.isSelfTransport());
        foundProduct.setProductCategory(product.getProductCategory());
        foundProduct.setDescription(product.getDescription());
        foundProduct.setBrand(product.getBrand());
        foundProduct.setDiscount(product.getDiscount());
        foundProduct.setProductPosition(product.getProductPosition());
        foundProduct.setDiscountProductPosition(product.getDiscountProductPosition());
        foundProduct.setPrice(product.getPrice());

        if(product.getSuggestedProductIdSlot1() != null){
            foundProduct.setSuggestedProductIdSlot1(product.getSuggestedProductIdSlot1());
        }

        if(product.getSuggestedProductIdSlot2() != null){
            foundProduct.setSuggestedProductIdSlot2(product.getSuggestedProductIdSlot2());
        }

        if(product.getSuggestedProductIdSlot3() != null){
            foundProduct.setSuggestedProductIdSlot3(product.getSuggestedProductIdSlot3());
        }

        if(product.getSuggestedProductIdSlot4() != null){
            foundProduct.setSuggestedProductIdSlot4(product.getSuggestedProductIdSlot4());
        }


        //Ako je novi jednak starom
        String slug = Slug.makeSlug(product.getSlug());
        if(!slug.equals(foundProduct.getSlug())){
            if(productRepository.findBySlug(slug).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_SLUG_ALREADY_EXIST);
            }
            foundProduct.setSlug(slug);
        }else{
            foundProduct.setSlug(foundProduct.getSlug());
        }

        //Ako je novi jednak starom
        String sku = product.getSku();
        if(!sku.equals(foundProduct.getSku())){
            if(productRepository.findBySku(sku).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_SKU_ALREADY_EXIST);
            }
            foundProduct.setSku(sku);
        }else{
            foundProduct.setSku(foundProduct.getSku());
        }

        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(new ProductElasticDTO(savedProduct));

        return savedProduct;
    }

    @Override
    public Product delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findOneById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();
        foundProduct.setDeleted(true);
        foundProduct.setDateDeleted(Calendar.getInstance().toInstant());

        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(ObjectMapperUtils.map(foundProduct, ProductElasticDTO.class));

        List<ProductAttributeCategory> productAttributeCategories = productAttributeCategoryRepository.findAllByProductId(savedProduct.getId());
        List<ProductAttributeValues> productAttributeValues = productAttributeValuesRepository.findAllByProductId(savedProduct.getId());

        for (ProductAttributeCategory productAttributeCategory:productAttributeCategories) {
            productAttributeCategory.setDeleted(true);
            productAttributeCategory.setDateDeleted(Calendar.getInstance().toInstant());
            productAttributeCategoryRepository.save(productAttributeCategory);
        }

        for(ProductAttributeValues productAttributeValues1 : productAttributeValues){
            productAttributeValues1.setDeleted(true);
            productAttributeValues1.setDateDeleted(Calendar.getInstance().toInstant());
            productAttributeValuesRepository.save(productAttributeValues1);
        }

        return savedProduct;
    }



    @Override
    public void uploadImage(Long id, MultipartFile[] images) throws IOException {
        int i = 0;
        for(MultipartFile uploadedFile : images) {
            String folderName = this.createDirectory().split("/")[1];
            String imageName = md5(uploadedFile.getOriginalFilename());

            byte[] bytes = uploadedFile.getBytes();

            Path path = Paths.get(documentPath + folderName + "/" + imageName  + ".png");
            Files.write(path, bytes);

            Product product = productRepository.getOne(id);
            Image image = new Image();

            if(i == 0 && product.getPrimaryImageUrl() == null){
               image.setPrimaryImage(true);
               product.setPrimaryImageUrl("images/" + folderName + "/" + imageName + ".png");
               productRepository.save(product);
            }

            image.setProduct(product);
            image.setName(imageName);
            image.setUrl("images/" + folderName + "/" + imageName + ".png");
            i++;

            imageRepository.save(image);
        }
    }

    @Override
    public Product toggleActivate(Long id) {
        Optional<Product> optionalProduct = productRepository.findOneById(id);

        Optional<ProductElasticDTO> elasticProductFound = productElasticRepository.findById(id);

        optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));
        elasticProductFound.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_NOT_EXIST));

        Product foundProduct = optionalProduct.get();
        ProductElasticDTO elasticProduct = elasticProductFound.get();

        List<Variation> productVariations = variationRepository.findAllByProductId(id);

        elasticProduct.setActive(!foundProduct.isActive());

        if(foundProduct.isActive()){
            foundProduct.setActive(false);
            if(!productVariations.isEmpty()){
                for(Variation variation : productVariations){
                    variation.setActive(false);
                    variationRepository.save(variation);
                }
            }
        }else{
            foundProduct.setActive(true);
            if(!productVariations.isEmpty()){
                for(Variation variation : productVariations){
                    variation.setActive(true);
                    variationRepository.save(variation);
                }
            }
        }


        Product savedProduct = productRepository.save(foundProduct);
        productElasticRepository.save(elasticProduct);

        return savedProduct;
    }

    @Override
    public void checkIfHasForeignKey(Long id, String entity) {
        List<Product> products = productRepository.findAll();
        if(entity.equals("brand")){
            for(Product product : products){
                if(product.getBrand().getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, BRAND_IN_PRODUCT_EXISTS);
                }
            }
        }else if(entity.equals("category")){
            for(Product product : products){
                if(product.getProductCategory().getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_CATEGORY_IN_PRODUCT_EXISTS);
                }
            }
        }else if(entity.equals("attribute")){
            List<AttributeValue> list = attributeValueRepository.findAllByAttributeId(id);
            for (AttributeValue attr: list) {
                List<ProductAttributeCategory> array = productAttributeCategoryRepository.findByAttributeValueId(attr.getId());
                if(array.size() > 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_IN_PRODUCT_EXISTS);
                }
            }
            List<ProductAttributes> productAttributesList = productAttributesRepository.findAllByAttributeIdAndDeletedFalse(id);
            if(productAttributesList.size() != 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_IN_PRODUCT_EXISTS);
            }

        }else if(entity.equals("attribute_value")){

            List<ProductAttributeCategory> array = productAttributeCategoryRepository.findByAttributeValueId(id);
            if(array.size() > 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_IN_PRODUCT_EXISTS);
            }

            List<ProductAttributeValues> productAttributeValues = productAttributeValuesRepository.findAllByAttributeValueIdAndDeletedFalse(id);
            if(productAttributeValues.size() != 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_IN_PRODUCT_EXISTS);
            }
        }
    }


    @Override
    public List<ProductAttributeValueDTO> getAllAttributeValuesForProductId(Long productId) {
        List<ProductAttributeAttrValueDTO> productAttributeAttrValueDTOS = productAttributeAttrValueRepository.findAllAttributeValuesForProductId(productId);
        List<ProductAttributeValueDTO> productAttributeValues = new ArrayList<ProductAttributeValueDTO>();

        for (ProductAttributeAttrValueDTO productAttributeAttrValueDTO : productAttributeAttrValueDTOS) {

            Attribute attr = attributeRepository.findById(productAttributeAttrValueDTO.getAttributeId()).get();

            ProductAttributeValueDTO productAttributeValueDTO = new ProductAttributeValueDTO();
            productAttributeValueDTO.setId(productAttributeAttrValueDTO.getId());
            productAttributeValueDTO.setProductId(productAttributeAttrValueDTO.getProductId());
            productAttributeValueDTO.setAttributeId(productAttributeAttrValueDTO.getAttributeId());
            productAttributeValueDTO.setAttributeValueId(productAttributeAttrValueDTO.getAttributeValueId());
            productAttributeValueDTO.setAttributeValueName(productAttributeAttrValueDTO.getAttributeValueName());
            productAttributeValueDTO.setAttributeName(attr.getName());

            productAttributeValues.add(productAttributeValueDTO);
        }

        return productAttributeValues;
    }


    @Override
    public List<ProductAttributeAttrValueDTO> getAllAttributeValsForProductId(Long productId) {
        return productAttributeAttrValueRepository.findAllAttributeValuesForProductId(productId);
    }

    @Override
    public List<ProductAttributeCategory> getAllAttributeCategoryForProduct(Long id) {
        return productAttributeCategoryRepository.findAllByProductId(id);
    }

    @Override
    public List<ProductAttributes> findForProductId(Long product_id) {
        return productAttributesRepository.findAllByProductIdAndDeletedFalse(product_id);
    }

    @Override
    public List<ProductDTO> getAllSuggestedProducts(Long id) {
        Product product = productRepository.getOne(id);
        List<Product> products = new ArrayList<>();

        if(product.getSuggestedProductIdSlot1() != null){
            Product suggestedProductSlot1 = productRepository.getOne(product.getSuggestedProductIdSlot1());
            products.add(suggestedProductSlot1);
        }

        if(product.getSuggestedProductIdSlot2() != null){
            Product suggestedProductSlot2 = productRepository.getOne(product.getSuggestedProductIdSlot2());
            products.add(suggestedProductSlot2);
        }

        if(product.getSuggestedProductIdSlot3() != null){
            Product suggestedProductSlot3 = productRepository.getOne(product.getSuggestedProductIdSlot3());
            products.add(suggestedProductSlot3);
        }

        if (product.getSuggestedProductIdSlot4() != null) {
            Product suggestedProductSlot4 = productRepository.getOne(product.getSuggestedProductIdSlot4());
            products.add(suggestedProductSlot4);
        }

        return listToDTOList(products);
    }



    private String createDirectory() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;

        String folderName = "/" + month + "-" + year;

        new File(documentPath + folderName).mkdirs();

        return folderName;
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        Long number = Long.valueOf(str);
        if(number >  1000){
            return false;
        }
        return true;
    }


}
