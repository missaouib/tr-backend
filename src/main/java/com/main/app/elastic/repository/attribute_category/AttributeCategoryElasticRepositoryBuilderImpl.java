package com.main.app.elastic.repository.attribute_category;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;

import static java.lang.String.valueOf;


@Repository
public class AttributeCategoryElasticRepositoryBuilderImpl implements AttributeCategoryElasticRepositoryBuilder{
    @Override
    public BoolQueryBuilder generateQuery(String filter,String name) {

        filter = filter.toLowerCase();
        name = name.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));

        if(name != null || name != ""){
            searchQuery.filter(QueryBuilders.termsQuery("name", name));
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("attribute_name", "*" + filter + "*"))

        );

        return searchQuery.must(boolQuery);
    }


}
