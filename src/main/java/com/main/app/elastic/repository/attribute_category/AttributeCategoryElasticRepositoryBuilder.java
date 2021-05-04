package com.main.app.elastic.repository.attribute_category;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface AttributeCategoryElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter,String attribute_name);
}
