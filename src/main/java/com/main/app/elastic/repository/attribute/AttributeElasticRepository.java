package com.main.app.elastic.repository.attribute;

import com.main.app.elastic.dto.attribute.AttributeElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeElasticRepository extends ElasticsearchRepository<AttributeElasticDTO, Long> {
}
