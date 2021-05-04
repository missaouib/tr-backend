package com.main.app.elastic.repository.order;

import com.main.app.elastic.dto.order.OrdersElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderElasticRepository extends ElasticsearchRepository<OrdersElasticDTO, Long> {
}
