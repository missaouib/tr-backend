package com.main.app.elastic.repository.attribute_value;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import static java.lang.String.valueOf;

@Repository
public class AttributeValueElasticRepositoryBuilderImpl implements AttributeValueElasticRepositoryBuilder {

    @Override
    public BoolQueryBuilder generateQuery(String filter, Long attributeId) {
        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        if(attributeId != -1){
            searchQuery.filter(QueryBuilders.termsQuery("attributeId", valueOf(attributeId)));
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*")));

        return searchQuery.must(boolQuery);
    }

}
