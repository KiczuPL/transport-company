package com.example.transportcompany.services;


import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.dto.OrderDto;
import com.example.transportcompany.model.requests.OrderRequest;
import com.example.transportcompany.model.specifications.OrderSpecification;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderSpecification orderSpecification;
    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order saveOrder(CreateOrderForm form) {

        Order order = new Order(form.getAddressFrom(),
                form.getAddressTo(),
                form.getCompanyId(),
                form.getPickUpDate(),
                form.getVehicleType());


        log.info("Saving order: {}", order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public OrderDto getOrder(Long id) {
        log.info("Getting order: {}:", id);
        Order order = orderRepository.getReferenceById(id);
        return new OrderDto(order,companyRepository.getReferenceById(order.getCompanyId()).getName());
    }

    @RolesAllowed({"ROLE_ADMIN"})
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

    @RolesAllowed({"ROLE_ADMIN"})
    public Map<String, Object> getOrders(OrderRequest request) {
        int pageNumber = request.getPageNumber() == null ? 0 : request.getPageNumber();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        log.info("Handling get orders request {}", request.toString());
        Page<Order> pageOrder = orderRepository.findAll(orderSpecification.getSpecification(request), pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", pageOrder.getContent().stream().map((order -> new OrderDto(order,companyRepository.getReferenceById(order.getCompanyId()).getName()))));
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed({"ROLE_USER"})
    public Map<String, Object> getOrdersByCompanyIdAndStatus(Long companyId, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDateTime"));
        log.info("Getting all orders with status {} for company with id {}", status, companyId);
        Page<Order> pageOrder = orderRepository.findAllByCompanyIdAndStatus(companyId, status, pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", pageOrder.getContent().stream().map((order -> new OrderDto(order,companyRepository.getReferenceById(order.getCompanyId()).getName()))));
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public Map<String, Object> getOrdersByStatus(Long id, OrderStatus status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("creationDate"));
        log.info("Getting all orders with status {}", status);
        Page<Order> pageOrder = orderRepository.findAllByStatus(status, pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", pageOrder.getContent().stream().map((order -> new OrderDto(order,companyRepository.getReferenceById(order.getCompanyId()).getName()))));
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed("ROLE_ADMIN")
    public void deleteAllOrdersFromCompany(Long companyId) {
        log.info("Deleting all orders from company with id: {}", companyId);
        orderRepository.deleteAllByCompanyId(companyId);
    }

}
