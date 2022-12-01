package com.example.transportcompany.controllers;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.dto.PageResponse;
import com.example.transportcompany.security.JwtConfig;
import com.example.transportcompany.services.OrderService;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final JwtConfig jwtConfig;


    @PostMapping("/save")
    public ResponseEntity<Order> saveOrder(@Validated @RequestBody CreateOrderForm form) {
        return new ResponseEntity<Order>(orderService.saveOrder(form), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<Order>(orderService.getOrder(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getOrderByStatus(@RequestParam(required = true) OrderStatus status,
                                                         @RequestParam(defaultValue = "0") Integer page,
                                                         @RequestParam(defaultValue = "10") Integer size,
                                                         HttpServletRequest request) {
        Company c = userService.getUser(request.getUserPrincipal().getName()).getCompany();
        return new ResponseEntity<Map<String,Object>>(orderService.getOrdersByCompanyIdAndStatus(c.getId(), status, page, size), HttpStatus.OK);
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
