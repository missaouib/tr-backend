package com.main.app.service.attribute_value;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.model.attribute_value.AttributeValue;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AttributeValueService {

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    AttributeValue getOne(Long id);

    AttributeValue save(AttributeValue attributeValue);

    AttributeValue edit(AttributeValue attributeValue, Long id);

    AttributeValue delete(Long id);

    List<AttributeValue> getAllByAttributeIdWithPageable(Long id, String searchParam, Pageable pageable);

    List<AttributeValue> getAllByAttributeId(Long id);

    List<AttributeValue> getAllByAttributeNameWithPageable(String name, Pageable pageable);

}
