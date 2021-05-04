package com.main.app.elastic.repository.brand;

import com.main.app.elastic.dto.brand.BrandElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandElasticRepository extends ElasticsearchRepository<BrandElasticDTO, Long> {
}
