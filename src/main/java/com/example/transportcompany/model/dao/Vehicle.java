package com.example.transportcompany.model.dao;

import com.example.transportcompany.model.VehicleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    @NotNull
    @NotBlank
    private String vehicleIdentifier;
    @NotNull
    @NotBlank
    private String registrationNumber;
    @NotNull
    private VehicleType type;

    public Vehicle(String vehicleIdentifier, String registrationNumber, VehicleType type) {
        this.vehicleIdentifier = vehicleIdentifier;
        this.registrationNumber = registrationNumber;
        this.type = type;
    }
}
