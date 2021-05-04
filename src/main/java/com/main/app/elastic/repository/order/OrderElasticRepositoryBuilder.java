package com.main.app.elastic.repository.order;

import org.elasticsearch.index.query.BoolQueryBuilder;

public interface OrderElasticRepositoryBuilder {

    BoolQueryBuilder generateQuery(String filter,String startDate,String endDate, String startPrice, String endPrice);

//    BoolQueryBuilder generateDateOrPriceFromToSearch(String date,String endDate, String startPrice, String endPrice);

}
