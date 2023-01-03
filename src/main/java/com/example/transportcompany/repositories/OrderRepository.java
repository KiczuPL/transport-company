package com.example.transportcompany.repositories;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByCompanyIdAndStatus(Long companyId, OrderStatus status, Pageable pageable);

    Page<Order> findAllByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findAllByCompanyNameContainingIgnoreCaseAndAddressFromContainingIgnoreCaseAndAddressToContainingIgnoreCaseAndPickUpDateBetweenAndStatus(String companyName,String addressFrom,String addressTo,LocalDate pickUpdateFrom,LocalDate pickUpDateTo, OrderStatus status, Pageable pageable);

    void deleteAllByCompanyId(Long companyId);
}
