package com.main.app.repository.attribute_value;

import com.main.app.domain.model.attribute_value.AttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {

    Page<AttributeValue> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<AttributeValue> findOneByName(String name);

    Optional<AttributeValue> findOneById(Long id);

    List<AttributeValue> findAllByAttributeId(Long attributeId);

    List<AttributeValue> findAllByName(String name);

    AttributeValue findByAttributeNameAndName(String attribute_name,String name);

    Page<AttributeValue> findAllByAttributeName(String name,Pageable pageable);
}
