package com.main.app.service.attribute_value;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.elastic.dto.attribute_value.AttributeValueElasticDTO;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepository;
import com.main.app.elastic.repository.attribute_value.AttributeValueElasticRepositoryBuilder;
import com.main.app.repository.attribute_value.AttributeValueRepository;
import com.main.app.service.product.ProductService;
import com.main.app.util.ObjectMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.main.app.converter.attribute_value.AttributeValueConverter.listToDTOList;
import static com.main.app.static_data.Messages.*;
import static com.main.app.util.Util.attrValuesToIds;
import static com.main.app.util.Util.attributeValuesToIds;

/**
 * The implementation of the service used for management of the AttributeValue data.
 *
 * @author Nikola
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeValueServiceImpl implements AttributeValueService {

    private final AttributeValueRepository attributeValueRepository;

    private final AttributeValueElasticRepository attributeValueElasticRepository;

    private final AttributeValueElasticRepositoryBuilder attributeValueElasticRepositoryBuilder;

    private final ProductService productService;



    @Override
    public Entities getAll() {
        List<AttributeValue> attributeValueList = attributeValueRepository.findAll();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(attributeValueList));
        entities.setTotal(attributeValueList.size());

        return entities;
    }

    @Override
    public Entities getAllBySearchParam(String searchParam, Pageable pageable) {
        Page<AttributeValueElasticDTO> pagedAttributeValues = attributeValueElasticRepository.search(attributeValueElasticRepositoryBuilder.generateQuery(searchParam, -1L), pageable);

        List<Long> ids = attributeValuesToIds(pagedAttributeValues);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeValue> attributeValueList = attributeValueRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        Entities entities = new Entities();
        entities.setEntities(listToDTOList(attributeValueList));
        entities.setTotal(pagedAttributeValues.getTotalElements());

        return entities;
    }

    @Override
    public List<AttributeValue> getAllByAttributeIdWithPageable(Long attributeId, String searchParam, Pageable pageable) {
        Page<AttributeValueElasticDTO> pagedAttributeValues = attributeValueElasticRepository.search(attributeValueElasticRepositoryBuilder.generateQuery(searchParam, attributeId), pageable);

        List<Long> ids = attributeValuesToIds(pagedAttributeValues);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeValue> attributeValueList = attributeValueRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        return attributeValueList;
    }

    @Override
    public List<AttributeValue> getAllByAttributeNameWithPageable(String name, Pageable pageable) {
        Page<AttributeValue> pagedAttributeValues = attributeValueRepository.findAllByAttributeName(name,pageable);

        List<Long> ids = attrValuesToIds(pagedAttributeValues);

        Pageable mySqlPaging = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
        List<AttributeValue> attributeValueList = attributeValueRepository.findAllByIdIn(ids, mySqlPaging).getContent();

        return attributeValueList;
    }



    @Override
    public AttributeValue getOne(Long id) {
        return attributeValueRepository.getOne(id);
    }


    @Override
    public AttributeValue save(AttributeValue attributeValue) {
//        Optional<AttributeValue> oneAttributeValue = attributeValueRepository.findOneByName(attributeValue.getName());
//
//        if(oneAttributeValue.isPresent()){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_WITH_NAME_ALREADY_EXIST);
//        }

        if(attributeValue.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_NAME_CANT_BE_NULL);
        }

        if(attributeValue.getAttribute() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_ATTRIBUTE_CANT_BE_NULL);
        }

        AttributeValue savedAttributeValue = attributeValueRepository.save(attributeValue);
        attributeValueElasticRepository.save(new AttributeValueElasticDTO(savedAttributeValue));

        return savedAttributeValue;
    }

    @Override
    public AttributeValue edit(AttributeValue attributeValue, Long id) {
//        Optional<AttributeValue> oneAttributeValue = attributeValueRepository.findOneByName(attributeValue.getName());
//
//        if(oneAttributeValue.isPresent() && !id.equals(oneAttributeValue.get().getId())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_WITH_NAME_ALREADY_EXIST);
//        }

        Optional<AttributeValue> optionalAttributeValue = attributeValueRepository.findOneById(id);

        if(!optionalAttributeValue.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_NOT_EXIST);
        }

        AttributeValue foundAttributeValue = optionalAttributeValue.get();

        if(attributeValue.getName() != null){
            foundAttributeValue.setName(attributeValue.getName());
        }

        AttributeValue savedAttributeValue = attributeValueRepository.save(foundAttributeValue);
        attributeValueElasticRepository.save(new AttributeValueElasticDTO(savedAttributeValue));

        return savedAttributeValue;
    }

    @Override
    public AttributeValue delete(Long id) {
        Optional<AttributeValue> optionalAttributeValue = attributeValueRepository.findOneById(id);

        if (!optionalAttributeValue.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ATTRIBUTE_VALUE_NOT_EXIST);
        }

        productService.checkIfHasForeignKey(id, "attribute_value");

        AttributeValue foundAttributeValue = optionalAttributeValue.get();
        foundAttributeValue.setDeleted(true);
        foundAttributeValue.setDateDeleted(Calendar.getInstance().toInstant());

        AttributeValue savedAttributeValue = attributeValueRepository.save(foundAttributeValue);
        attributeValueElasticRepository.save(ObjectMapperUtils.map(foundAttributeValue, AttributeValueElasticDTO.class));

        return savedAttributeValue;
    }



    @Override
    public List<AttributeValue> getAllByAttributeId(Long attributeId) {
        return attributeValueRepository.findAllByAttributeId(attributeId);
    }

}
