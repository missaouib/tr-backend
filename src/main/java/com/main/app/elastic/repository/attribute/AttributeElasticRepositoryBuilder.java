package com.main.app.elastic.repository.attribute;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface AttributeElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter, boolean participatesInVariation, boolean enteredManually);

}
