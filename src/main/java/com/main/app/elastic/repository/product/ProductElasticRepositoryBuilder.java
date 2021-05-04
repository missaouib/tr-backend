package com.main.app.elastic.repository.product;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface ProductElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter, Long productType);


}
