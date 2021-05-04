package com.main.app.elastic.repository.variation;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface VariationElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter, String productId);

}
