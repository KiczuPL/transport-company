package com.example.transportcompany.services;


import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dao.User;
import com.example.transportcompany.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public Order addOrder(Order order){
        return orderRepository.save(order);
    }

    public Order getOrder(Long id){
        return orderRepository.getReferenceById(id);
    }

    public Order editOrder(Order order){
        try{
            Order orderById = orderRepository.getReferenceById(order.getId());
        }catch (Exception exception){
            throw new IllegalArgumentException(exception.getMessage());
        }

        log.info("Saving order: "+order.toString());
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id){
        orderRepository.deleteById(id);
    }

    public void setOrderStatus(Long id, OrderStatus status){
        Order order = orderRepository.getReferenceById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }

    public List<Order> getOrdersByCompanyIdAndStatus(Long id, OrderStatus status, int page, int size){
        PageRequest pageRequest =  PageRequest.of(page,size, Sort.by("creationDate"));
        return orderRepository.findAllByCompanyIdAndStatus(id,status,pageRequest);
    }

    public List<Order> getOrdersByStatus(Long id, OrderStatus status, int page, int size){
        PageRequest pageRequest =  PageRequest.of(page,size, Sort.by("creationDate"));
        return orderRepository.findAllByCompanyIdAndStatus(id,status,pageRequest);
    }
}
