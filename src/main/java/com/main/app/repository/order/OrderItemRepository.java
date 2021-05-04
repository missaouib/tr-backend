package com.main.app.repository.order;

import com.main.app.domain.model.order_item.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByCustomerOrderId(Long customerOrderId);

}
