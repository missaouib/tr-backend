package com.main.app.domain.model.order_item;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.variation.Variation;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class OrderItem extends AbstractEntity {

    @ManyToOne
    private CustomerOrder customerOrder;

    private int quantity;

    @ManyToOne
    private Variation variation;

    @ManyToOne
    private Product product;

}
