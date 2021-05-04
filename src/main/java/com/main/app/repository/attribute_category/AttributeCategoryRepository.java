package com.main.app.repository.attribute_category;

import com.main.app.domain.model.attribute_category.AttributeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeCategoryRepository extends JpaRepository<AttributeCategory, Long> {

    Page<AttributeCategory> findAll(Pageable pageable);

    Page<AttributeCategory> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<AttributeCategory> findOneByName(String name);

    List<AttributeCategory> findAllByName(String name);

    Page<AttributeCategory> findAllByName(String name,Pageable pageable);

    Optional<AttributeCategory> findOneById(Long id);

    Optional<AttributeCategory> findOneByAttributeId(Long attribute_id);
}
