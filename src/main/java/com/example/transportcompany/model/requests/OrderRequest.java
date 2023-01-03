package com.example.transportcompany.model.requests;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.VehicleType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderRequest {
    String companyName;
    String addressFrom;
    String addressTo;
    LocalDate pickUpDateFrom;
    LocalDate pickUpDateTo;
    VehicleType vehicleType;
    LocalDateTime creationDateTimeFrom;
    LocalDateTime creationDateTimeTo;
    OrderStatus status;

    Integer pageNumber;
    Integer pageSize;
}
