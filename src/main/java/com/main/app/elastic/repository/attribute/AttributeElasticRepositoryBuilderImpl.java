package com.main.app.elastic.repository.attribute;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

@Repository
public class AttributeElasticRepositoryBuilderImpl implements AttributeElasticRepositoryBuilder {

    @Override
    public BoolQueryBuilder generateQuery(String filter,boolean participatesInVariation, boolean enteredManually) {
        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        if(participatesInVariation == true){
            searchQuery.filter(QueryBuilders.termsQuery("participatesInVariation", "true"));
        }

        if(enteredManually == true){
            searchQuery.filter(QueryBuilders.termsQuery("enteredManually", "true"));
        }


        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*"))
        );

        return searchQuery.must(boolQuery);
    }

}
