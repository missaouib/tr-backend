package com.main.app.elastic.repository.attribute_category_unique;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface AttributeCategoryUniqueElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter);
}
