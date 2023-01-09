package com.example.transportcompany.controllers;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dto.CreateOrderForm;
import com.example.transportcompany.model.dto.OrderDto;
import com.example.transportcompany.model.requests.OrderRequest;
import com.example.transportcompany.security.JwtConfig;
import com.example.transportcompany.services.OrderService;
import com.example.transportcompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;


    @PostMapping("/save")
    public ResponseEntity<Order> saveOrder(@Validated @RequestBody CreateOrderForm form) {
        return new ResponseEntity<Order>(orderService.saveOrder(form), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<OrderDto>(orderService.getOrder(id), HttpStatus.OK);
    }

    @PostMapping("/all")
    public ResponseEntity<Map<String, Object>> getOrders(@RequestBody OrderRequest request) {
        return new ResponseEntity<Map<String, Object>>(orderService.getOrders(request), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrderByStatus(@RequestParam(required = true) OrderStatus status,
                                                                @RequestParam(required = false) String addressFrom,
                                                                @RequestParam(required = false) String addressTo,
                                                                @RequestParam(required = false) String pickUpDateFrom,
                                                                @RequestParam(required = false) String pickUpDateTo,
                                                                @RequestParam(defaultValue = "0") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size,
                                                                HttpServletRequest request) {
        Company c = userService.getUser(request.getUserPrincipal().getName()).getCompany();
        LocalDate dateFrom = (pickUpDateFrom== null || pickUpDateFrom.isBlank()) ? null : LocalDate.parse(pickUpDateFrom);
        LocalDate dateTo = (pickUpDateTo == null  || pickUpDateTo.isBlank()) ? null : LocalDate.parse(pickUpDateTo);
        OrderRequest request1 = new OrderRequest(c.getName(), addressFrom, addressTo, dateFrom, dateTo, null, null, null, status, page, size);
        return new ResponseEntity<Map<String, Object>>(orderService.getOrdersByCompanyIdAndStatus(request1), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto order) {
        System.out.println(order.toString());
        return new ResponseEntity<OrderDto>(orderService.updateOrder(order), HttpStatus.OK);
    }
    @PutMapping("/test")
    public ResponseEntity<String> test(@RequestBody OrderDto order) {
        System.out.println(order.toString());
        return new ResponseEntity<String>("ddddddddddd", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<OrderDto>(HttpStatus.OK);
    }


}
