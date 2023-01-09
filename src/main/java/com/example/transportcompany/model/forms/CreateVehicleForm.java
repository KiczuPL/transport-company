package com.example.transportcompany.model.forms;

import com.example.transportcompany.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateVehicleForm {
    private String vehicleIdentifier;
    private String registrationNumber;
    private VehicleType type;
}
