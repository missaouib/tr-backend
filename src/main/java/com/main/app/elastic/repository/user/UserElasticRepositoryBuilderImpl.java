package com.main.app.elastic.repository.user;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

@Repository
public class UserElasticRepositoryBuilderImpl implements UserElasticRepositoryBuilder {

    @Override
    public BoolQueryBuilder generateQuery(String filter) {
        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("surname", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("email", "*" + filter + "*")));

        return searchQuery.must(boolQuery);
    }

}
