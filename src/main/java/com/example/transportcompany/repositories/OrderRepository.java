package com.example.transportcompany.repositories;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCompanyIdAndStatus(Long companyId, OrderStatus status, Pageable pageable);

    List<Order> findAllByStatus(OrderStatus status, Pageable pageable);
}
