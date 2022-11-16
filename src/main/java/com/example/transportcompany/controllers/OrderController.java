package com.example.transportcompany.controllers;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.security.JwtConfig;
import com.example.transportcompany.services.OrderService;
import com.example.transportcompany.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final JwtConfig jwtConfig;

    @PostMapping("/save")
    public ResponseEntity<Order> saveOrder(@Validated @RequestBody Order order) {
        return new ResponseEntity<Order>(orderService.saveOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<Order>(orderService.getOrder(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderByStatus(@RequestParam(required = true) OrderStatus status,
                                                        @RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size,
                                                        HttpServletRequest request) throws Exception {
        Company c = jwtConfig.getCompanyOfUserJwtAssignedToJwt(request);
        return new ResponseEntity<List<Order>>(orderService.getOrdersByCompanyIdAndStatus(c.getId(), status, page, size), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Order> updateOrder(@Validated @RequestBody Order order) {
        return new ResponseEntity<Order>(orderService.updateOrder(order), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Company> deleteOrder(@RequestBody Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<Company>(HttpStatus.OK);
    }

}
