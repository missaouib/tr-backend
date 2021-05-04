package com.main.app.elastic.repository.attribute_value;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface AttributeValueElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter, Long attributeId);

}
