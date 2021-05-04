package com.main.app.elastic.repository.category;

import com.main.app.elastic.dto.category.CategoryElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryElasticRepository extends ElasticsearchRepository<CategoryElasticDTO, Long> {
}
