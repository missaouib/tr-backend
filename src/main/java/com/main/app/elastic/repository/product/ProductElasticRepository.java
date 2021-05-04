package com.main.app.elastic.repository.product;

import com.main.app.elastic.dto.product.ProductElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ProductElasticDTO, Long> {
}
