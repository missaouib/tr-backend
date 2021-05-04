package com.main.app.service.attribute_category;

import com.main.app.converter.attribute.AttributeConverter;
import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.attribute_category.AttributeCategoryDTO;
import com.main.app.domain.dto.attribute_category.AttributeCategoryUniqueDTO;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import com.main.app.domain.model.product_attribute_category.ProductAttributeCategory;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryUniqueElasticDTO;
import com.main.app.elastic.repository.attribute_category.AttributeCategoryElasticRepository;
import com.main.app.elastic.repository.attribute_category.AttributeCategoryElasticRepositoryBuilder;
import com.main.app.elastic.repository.attribute_category_unique.AttributeCategoryUniqueElasticRepository;
import com.main.app.elastic.repository.attribute_category_unique.AttributeCategoryUniqueElasticRepositoryBuilder;
import com.main.app.repository.attribute_category.AttributeCategoryRepository;
import com.main.app.repository.product_attribute_category.ProductAttributeCategoryRepository;
import com.main.app.service.attribute.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static com.main.app.converter.attribute_category.AttributeCategoryConverter.listToDTOList;
import static com.main.app.converter.attribute_category.AttributeCategoryConverter.listUniqueToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.*;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeCategoryServiceImpl implements AttributeCategoryService {

    private final AttributeService attributeService;

    private final AttributeCategoryRepository attributeCategoryRepository;

    private final ProductAttributeCategoryRepository productAttributeCategoryRepository;

    private final AttributeCategoryElasticRepository attributeCategoryElasticRepository;

    private final AttributeCategoryElasticRepositoryBuilder attributeCategoryElasticRepositoryBuilder;

    private final AttributeCategoryUniqueElasticRepository attributeCategoryUniqueElasticRepository;

    private final AttributeCategoryUniqueElasticRepositoryBuilder attributeCategoryUniqueElasticRepositoryBuilder;

    @Override
    public Entities getAll(Pageable pageable) {

        List<AttributeCategory> attributeCategories = attributeCategoryRepository.findAll();
        ArrayList<AttributeCategoryUniqueDTO> uniqueNames = new ArrayList<>();
        ArrayList<AttributeCategoryUniqueDTO> uniqueObject = new ArrayList<>();

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

        List<Long> ids = uniqueToIds(uniqueNames);

        ArrayList<Long> pageIds = new ArrayList<>();
        for(int i=(pageable.getPageNumber()*pageable.getPageSize());i<ids.size();i++){
            pageIds.add(ids.get(i));
        }

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeCategory> content = attributeCategoryRepository.findAllByIdIn(pageIds, mySqlPaging).getContent();


        for (AttributeCategory c: content) {
            uniqueObject.add(new AttributeCategoryUniqueDTO(c.getId(),c.getName(),c.getDateCreated()));

        }

        Entities entities = new Entities();
        entities.setEntities(listUniqueToDTOList(uniqueObject));
        entities.setTotal(uniqueNames.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String name, String searchParam, Pageable pageable) {

        Page<AttributeCategoryElasticDTO> pagedAttributeCategory = attributeCategoryElasticRepository.search(attributeCategoryElasticRepositoryBuilder.generateQuery(searchParam,name), pageable);
        List<Long> ids = attributeCategoryToIds(pagedAttributeCategory);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeCategory> content = attributeCategoryRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(content));
        entities.setTotal(pagedAttributeCategory.getTotalElements());

        return entities;
    }

    @Override
    public Entities getAllSearchUnique(String searchParam, Pageable pageable) {

        Page<AttributeCategoryUniqueElasticDTO> pagedAttributeCategory = attributeCategoryUniqueElasticRepository.search(attributeCategoryUniqueElasticRepositoryBuilder.generateQuery(searchParam), pageable);
        List<Long> ids = uniqueElasticToIds(pagedAttributeCategory);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeCategory> content = attributeCategoryRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(content));
        entities.setTotal(pagedAttributeCategory.getTotalElements());

        return entities;
    }

    @Override
    public Entities save(AttributeCategoryDTO attributeCategoryDTO) {

        if(attributeCategoryDTO.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_CATEGORY_NAME_NOT_EXIST);
        }
        if(attributeCategoryDTO.getAttributeIds().size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_IDS_CANT_BE_NULL);
        }

        for (Long attributeId: attributeCategoryDTO.getAttributeIds()) {
            if(attributeService.getOne(attributeId) == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_NOT_EXIST);
            }
            Attribute attribute = attributeService.getOne(attributeId);
            AttributeCategory attributeCategory = new AttributeCategory(attributeCategoryDTO.getName(),attribute.getName(),attribute);

            AttributeCategory saved = attributeCategoryRepository.save(attributeCategory);
            attributeCategoryElasticRepository.save(new AttributeCategoryElasticDTO(saved));
        }


        //Vrati podatke na front
        List<AttributeCategory> attributeCategories = attributeCategoryRepository.findAll();
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
        for (AttributeCategoryUniqueDTO unique: uniqueNames) {
            attributeCategoryUniqueElasticRepository.save(new AttributeCategoryUniqueElasticDTO(unique));
        }
        Entities entities = new Entities();
        entities.setEntities(listToDTOList(attributeCategories));
        entities.setTotal(attributeCategories.size());

        return entities;
    }

    @Override
    public  List<AttributeCategory> edit(AttributeCategoryDTO attributeCategoryDTO) {
        List<AttributeCategory> listAttrCat = attributeCategoryRepository.findAllByName(attributeCategoryDTO.getOldName());

        for (AttributeCategory item: listAttrCat) {
            item.setName(attributeCategoryDTO.getName());
            attributeCategoryRepository.save(item);
            attributeCategoryElasticRepository.save(new AttributeCategoryElasticDTO(item));
        }

        return listAttrCat;
    }

    @Override
    public List<AttributeCategory> delete(String name){

        List<ProductAttributeCategory> list = productAttributeCategoryRepository.findByName(name);
        if(list.size() > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCTS_EXISTS_WITH_THIS_CATEGORY_ATTRIBUTES);
        }

        List<AttributeCategory> listAttrCat = attributeCategoryRepository.findAllByName(name);

        for (AttributeCategory item: listAttrCat) {
            item.setDeleted(true);
            item.setDateDeleted(Calendar.getInstance().toInstant());
            attributeCategoryRepository.save(item);
            attributeCategoryElasticRepository.save(new AttributeCategoryElasticDTO(item));
        }

        return listAttrCat;
    }

    @Override
    public AttributeCategory deleteAttribute(String name, Long id) {

        AttributeCategory attrCat = attributeCategoryRepository.findOneByAttributeId(id).get();
        List<ProductAttributeCategory> list = productAttributeCategoryRepository.findByAttributeCategoryId(attrCat.getId());
        if(list.size() > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCTS_EXISTS_WITH_THIS_ATTRIBUTES);
        }


        List<AttributeCategory> listAttrCat = attributeCategoryRepository.findAllByName(name);
        AttributeCategory deleted = new AttributeCategory();

        for (AttributeCategory item: listAttrCat) {
            if(item.getAttribute().getId() == id){
                item.setDeleted(true);
                item.setDateDeleted(Calendar.getInstance().toInstant());
                deleted = item;
                attributeCategoryRepository.save(item);
                attributeCategoryElasticRepository.save(new AttributeCategoryElasticDTO(item));
            }
        }
        return deleted;
    }

    @Override
    public Entities getAllByAttributeCategoryName(String name,Pageable pageable) {
        Page<AttributeCategory> pagedCategory = attributeCategoryRepository.findAllByName(name,pageable);

        List<Long> ids = attributeCategoryToIdsObject(pagedCategory);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeCategory> content = attributeCategoryRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(content));
        entities.setTotal(pagedCategory.getTotalElements());

        return entities;
    }

    @Override
    public AttributeCategory getOne(Long id) {
        return null;
    }

}
