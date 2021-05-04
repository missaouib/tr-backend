package com.main.app.elastic.repository.attribute_category_unique;

import com.main.app.elastic.dto.attribute_category.AttributeCategoryElasticDTO;
import com.main.app.elastic.dto.attribute_category.AttributeCategoryUniqueElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttributeCategoryUniqueElasticRepository extends ElasticsearchRepository<AttributeCategoryUniqueElasticDTO, Long> {
}
