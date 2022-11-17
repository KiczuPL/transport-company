package com.example.transportcompany.model.dao;

import com.example.transportcompany.model.OrderStatus;
import com.example.transportcompany.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    public Order(@NotNull @NotBlank String addressFrom, @NotNull @NotBlank String addressTo, @NotNull Long companyId, @NotNull LocalDate pickUpDate, @NotNull VehicleType vehicleType) {
        this.companyId = companyId;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.vehicleType = vehicleType;
        this.pickUpDate = pickUpDate;
    }
}
