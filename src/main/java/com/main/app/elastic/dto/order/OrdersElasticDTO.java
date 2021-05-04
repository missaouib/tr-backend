package com.main.app.elastic.dto.order;

import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.elastic.dto.EntityElasticDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "order")
public class OrdersElasticDTO extends EntityElasticDTO {


    @Field(type = FieldType.Text, fielddata = true)
    private String buyerName;

    @Field(type = FieldType.Text, fielddata = true)
    private String buyerSurname;

    @Field(type = FieldType.Text, fielddata = true)
    private String buyerEmail;

    @Field(type = FieldType.Text, fielddata = true)
    private String buyerCity;

    @Field(type = FieldType.Text, fielddata = true)
    private String status;

    @Field(type = FieldType.Text, fielddata = true)
    private String paymentProcessType;

    @Field(type = FieldType.Text, fielddata = true)
    private String deliveryType;

    @Field(type = FieldType.Float, fielddata = true)
    private Integer totalPrice;

    private Long dateCreated;

    public OrdersElasticDTO(CustomerOrder order){
        super(order.getId());
        this.buyerName = order.getBuyerName();
        this.buyerSurname = order.getBuyerSurname();
        this.buyerEmail = order.getBuyerEmail();
        this.buyerCity = order.getBuyerCity();
        this.status = order.getStatus();
        this.paymentProcessType = order.getPaymentProcessType();
        this.deliveryType = order.getDeliveryType();
        this.totalPrice = order.getTotalPrice();
        this.dateCreated = order.getDateCreated().toEpochMilli()/1000;
    }



}
