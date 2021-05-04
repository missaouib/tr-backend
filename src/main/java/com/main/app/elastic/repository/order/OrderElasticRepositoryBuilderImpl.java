package com.main.app.elastic.repository.order;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;

import static org.hibernate.annotations.common.util.StringHelper.isEmpty;

@Repository
public class OrderElasticRepositoryBuilderImpl implements OrderElasticRepositoryBuilder{

    @Override
    public BoolQueryBuilder generateQuery(String filter,String startDate,String endDate, String startPrice, String endPrice) {

        filter = filter.toLowerCase();

        BoolQueryBuilder searchQuery = QueryBuilders.boolQuery();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        searchQuery.filter(QueryBuilders.termsQuery("deleted", "false"));



        if (!isEmpty(startDate) && !isEmpty(endDate)) {
            boolQuery.must(QueryBuilders
                    .rangeQuery("dateCreated").gte(Long.valueOf(startDate)).lte(Long.valueOf(endDate)));
        }
        if (!isEmpty(startPrice)) {
            boolQuery.must(QueryBuilders
                    .rangeQuery("totalPrice").gte(Integer.valueOf(startPrice)));
        }
        if (!isEmpty(endPrice)) {
            boolQuery.must(QueryBuilders
                    .rangeQuery("totalPrice").lte(Integer.valueOf(endPrice)));
        }


        boolQuery.must(
                new BoolQueryBuilder()
                        .should(QueryBuilders.wildcardQuery("buyerName", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("buyerSurname", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("buyerEmail", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("buyerCity", "*" + filter + "*"))
                        .should(QueryBuilders.wildcardQuery("status", "*" + filter + "*"))
                      );
        return searchQuery.must(boolQuery);
    }


    private static boolean isEmpty(String check){
            if(check.equals("") || check.equals("undefined") || StringHelper.isEmpty(check) || check == null){
                return true;
            }
            return false;
    }

}
