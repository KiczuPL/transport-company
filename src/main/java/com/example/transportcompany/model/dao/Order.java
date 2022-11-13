package com.example.transportcompany.model.dao;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.VehicleType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private Long companyId;
    @NotNull
    @NotBlank
    private String addressFrom;
    @NotNull
    @NotBlank
    private String addressTo;
    @NotNull
    private VehicleType vehicleType;
    @NotNull
    private LocalDate pickUpDate;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    @NotNull
    private OrderStatus status = OrderStatus.PLACED;
    @ManyToOne(fetch = FetchType.EAGER)
    private Vehicle assignedVehicle;
}
