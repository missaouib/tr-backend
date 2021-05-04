package com.main.app.elastic.repository.category;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface CategoryElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter);

}
