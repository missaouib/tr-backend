package com.main.app.repository.attribute;

import com.main.app.domain.model.attribute.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Page<Attribute> findAllByIdIn(List<Long> idsList, Pageable pageable);

    Optional<Attribute> findOneByName(String name);

    Optional<Attribute> findOneById(Long id);

    Page<Attribute> findAllByParticipatesInVariationFalse(Pageable pageable);
}
