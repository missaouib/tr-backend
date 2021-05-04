package com.main.app.elastic.repository.attribute_value;

import com.main.app.elastic.dto.attribute_value.AttributeValueElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeValueElasticRepository extends ElasticsearchRepository<AttributeValueElasticDTO, Long> {
}
