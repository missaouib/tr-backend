package com.main.app.elastic.repository.variation;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

@Repository
public class VariationElasticRepositoryBuilderImpl implements VariationElasticRepositoryBuilder {

    @Override
    public BoolQueryBuilder generateQuery(String filter, String productId) {
        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        searchQuery.filter(QueryBuilders.termsQuery("productId", productId));

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("name", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("sku", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("slug", "*" + filter + "*")));

        return searchQuery.must(boolQuery);
    }

}
