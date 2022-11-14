package com.example.transportcompany.services;


import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order saveOrder(Order order) {
        log.info("Saving order: " + order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order getOrder(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order updateOrder(Order order) {
        try {
            Order orderById = orderRepository.getReferenceById(order.getId());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        log.info("Updating order: " + order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteOrderById(Long id) {
        Order order = orderRepository.getReferenceById(id);
        log.info("Deleting order: " + order.toString());
        orderRepository.deleteById(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<Order> getOrdersByCompanyIdAndStatus(Long companyId, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDateTime"));
        return orderRepository.findAllByCompanyIdAndStatus(companyId, status, pageRequest);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<Order> getOrdersByStatus(Long id, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDate"));
        return orderRepository.findAllByCompanyIdAndStatus(id, status, pageRequest);
    }
}
