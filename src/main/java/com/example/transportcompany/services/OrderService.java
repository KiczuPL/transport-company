package com.example.transportcompany.services;


import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dao.Vehicle;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.dto.OrderDto;
import com.example.transportcompany.model.requests.OrderRequest;
import com.example.transportcompany.repositories.CompanyRepository;
import com.example.transportcompany.repositories.OrderRepository;
import com.example.transportcompany.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final VehicleRepository vehicleRepository;

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Order saveOrder(CreateOrderForm form) {
        Order order = new Order(
                form.getAddressFrom(),
                form.getAddressTo(),
                companyRepository.getReferenceById(form.getCompanyId()),
                form.getPickUpDate(),
                form.getVehicleType());

        log.info("Saving order: {}", order.toString());
        return orderRepository.save(order);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public OrderDto getOrder(Long id) {
        log.info("Getting order: {}:", id);
        Order order = orderRepository.getReferenceById(id);
        return new OrderDto(order);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    public OrderDto updateOrder(OrderDto order) {
        Order orderById;
        try {
            orderById = orderRepository.getReferenceById(order.getId());
        } catch (Exception exception) {
            throw new NoSuchElementException(exception.getMessage());
        }

        log.info("Updating order: {} to {}", orderById.toString(), order.toString());
        Order updated = new Order(order);
        updated.setCreationDateTime(orderById.getCreationDateTime());
        return new OrderDto(orderRepository.save(updated));
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteOrderById(Long id) {
        Order order = orderRepository.getReferenceById(id);
        log.info("Deleting order: {}", order.toString());
        orderRepository.deleteById(id);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Map<String, Object> getOrders(OrderRequest request) {
        int pageNumber = request.getPageNumber() == null ? 0 : request.getPageNumber();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("creationDateTime"));
        log.info("Handling get orders request {}", request.toString());
        Page<Order> pageOrder = orderRepository.findAllByCompanyNameContainingIgnoreCaseAndAddressFromContainingIgnoreCaseAndAddressToContainingIgnoreCaseAndPickUpDateBetweenAndStatus(
                request.getCompanyName(),
                request.getAddressFrom(),
                request.getAddressTo(),
                request.getPickUpDateFrom() != null ? request.getPickUpDateFrom() : LocalDate.of(1900, 1, 1),
                request.getPickUpDateTo() != null ? request.getPickUpDateTo() : LocalDate.of(9999, 1, 1),
                request.getStatus(),
                pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", pageOrder.getContent().stream().map((OrderDto::new)));
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed({"ROLE_USER"})
    public Map<String, Object> getOrdersByCompanyIdAndStatus(OrderRequest request) {
        int pageNumber = request.getPageNumber() == null ? 0 : request.getPageNumber();
        int pageSize = request.getPageSize() == null ? 10 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("creationDateTime"));
        log.info("Getting all orders with status {} for company  {}", request.getStatus(), request.getCompanyName());
        Page<Order> pageOrder = orderRepository.findAllByCompanyNameContainingIgnoreCaseAndAddressFromContainingIgnoreCaseAndAddressToContainingIgnoreCaseAndPickUpDateBetweenAndStatus(
                request.getCompanyName(),
                request.getAddressFrom(),
                request.getAddressTo(),
                request.getPickUpDateFrom() != null ? request.getPickUpDateFrom() : LocalDate.of(1900, 1, 1),
                request.getPickUpDateTo() != null ? request.getPickUpDateTo() : LocalDate.of(9999, 1, 1),
                request.getStatus(),
                pageRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("orders", pageOrder.getContent().stream().map((OrderDto::new)));
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
        response.put("orders", pageOrder.getContent().stream().map((OrderDto::new)));
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

    @RolesAllowed({"ROLE_ADMIN"})
    public void assignVehicle(Long orderId, Long vehicleId){
        Order order = orderRepository.getReferenceById(orderId);
        log.warn("{}",order);
        log.warn("{}----{}",orderId,vehicleId);
        Vehicle vehicle = vehicleRepository.getReferenceById(vehicleId);
        log.warn("{}",vehicle);
        order.setAssignedVehicle(vehicle);
        orderRepository.save(order);
        log.info("Assigned vehicle: {} to order: {}",vehicle,order);
    }

}
