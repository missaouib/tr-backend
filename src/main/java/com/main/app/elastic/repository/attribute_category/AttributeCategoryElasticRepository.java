package com.main.app.elastic.repository.attribute_category;

import com.main.app.elastic.dto.attribute_category.AttributeCategoryElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttributeCategoryElasticRepository extends ElasticsearchRepository<AttributeCategoryElasticDTO, Long> {
}
