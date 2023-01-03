package com.example.transportcompany.model.dto;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Company;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dao.Vehicle;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long id;
    private Company company;
    private String addressFrom;
    private String addressTo;
    private VehicleType vehicleType;
    private LocalDate pickUpDate;
    private LocalDateTime creationDateTime;
    private OrderStatus status;
    private Vehicle assignedVehicle;


    public OrderDto(Order order) {
        this.id = order.getId();
        this.company=order.getCompany();
        this.addressFrom = order.getAddressFrom();
        this.addressTo = order.getAddressTo();
        this.vehicleType = order.getVehicleType();
        this.pickUpDate = order.getPickUpDate();
        this.creationDateTime = order.getCreationDateTime();
        this.status = order.getStatus();
        this.assignedVehicle = order.getAssignedVehicle();
    }
}
