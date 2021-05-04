package com.main.app.repository.order;

import com.main.app.domain.model.order.CustomerOrder;
import com.main.app.domain.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    Page<CustomerOrder> findAllByIdInAndDeletedFalse(List<Long> idsList, Pageable pageable);

    Optional<CustomerOrder> findById(Long id);

}
