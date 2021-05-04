package com.main.app.elastic.repository.user;

import com.main.app.elastic.dto.user.UserElasticDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserElasticRepository extends ElasticsearchRepository<UserElasticDTO, Long> {
}
