package com.main.app.domain.model.order;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.order_item.OrderItem;
import com.main.app.domain.model.user.User;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class CustomerOrder extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer totalPrice;

    private String status;

    private String paymentProcessType;

    private String deliveryType;

    private String buyerName;

    private String buyerSurname;

    private String buyerEmail;

    private String buyerCity;

    private String buyerAddress;

    private String buyerPhone;

    private String cityPostalCode;

    private String note;

    private String shop;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customerOrder")
    private List<OrderItem> orderItems = new ArrayList<>();

}
