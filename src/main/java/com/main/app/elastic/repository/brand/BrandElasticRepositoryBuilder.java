package com.main.app.elastic.repository.brand;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface BrandElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter);

}
