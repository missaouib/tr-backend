package com.main.app.elastic.repository.user;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface UserElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter);

}
