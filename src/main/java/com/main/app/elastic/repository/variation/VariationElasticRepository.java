package com.main.app.elastic.repository.variation;

import com.main.app.elastic.dto.variation.VariationElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariationElasticRepository extends ElasticsearchRepository<VariationElasticDTO, Long> {
}
