package com.main.app.service.attribute_category;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.attribute_category.AttributeCategoryDTO;
import com.main.app.domain.model.attribute_category.AttributeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AttributeCategoryService {

    Entities getAll(Pageable pageable);

    Entities getAllBySearchParam(String attribute_name, String searchParam, Pageable pageable);

    AttributeCategory getOne(Long id);

    Entities save(AttributeCategoryDTO attributeCategoryDTO);

    List<AttributeCategory> edit(AttributeCategoryDTO attributeValue);

    List<AttributeCategory> delete(String name);

    Entities getAllByAttributeCategoryName(String name, Pageable pageable);

    Entities getAllSearchUnique(String searchParam, Pageable pageable);

    AttributeCategory deleteAttribute(String name, Long id);
}
