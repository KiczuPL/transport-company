package com.example.transportcompany.services;


import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.dto.PageResponse;
import com.example.transportcompany.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order saveOrder(CreateOrderForm form) {

        Order order= new Order(form.getAddressFrom(),
                form.getAddressTo(),
                form.getCompanyId(),
                form.getPickUpDate(),
                form.getVehicleType());


        log.info("Saving order: {}", order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order getOrder(Long id) {
        log.info("Getting order: {}:",id);
        return orderRepository.getReferenceById(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order updateOrder(Order order) {
        try {
            Order orderById = orderRepository.getReferenceById(order.getId());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        log.info("Updating order: {}", order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteOrderById(Long id) {
        Order order = orderRepository.getReferenceById(id);
        log.info("Deleting order: {}", order.toString());
        orderRepository.deleteById(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Map<String, Object> getOrdersByCompanyIdAndStatus(Long companyId, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDateTime"));
        log.info("Getting all orders with status {} for company with id {}", status,companyId);
        Page<Order> pageOrder = orderRepository.findAllByCompanyIdAndStatus(companyId, status, pageRequest);
        Map<String,Object> response = new HashMap<>();
        response.put("orders",pageOrder.getContent());
        response.put("currentPage",pageOrder.getNumber());
        response.put("totalItems",pageOrder.getTotalElements());
        response.put("totalPages",pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public Map<String, Object> getOrdersByStatus(Long id, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDate"));
        log.info("Getting all orders with status {}", status);
        Page<Order> pageOrder = orderRepository.findAllByStatus( status, pageRequest);
        Map<String,Object> response = new HashMap<>();
        response.put("orders",pageOrder.getContent());
        response.put("currentPage",pageOrder.getNumber());
        response.put("totalItems",pageOrder.getTotalElements());
        response.put("totalPages",pageOrder.getTotalPages());
        return response;
    }
}
