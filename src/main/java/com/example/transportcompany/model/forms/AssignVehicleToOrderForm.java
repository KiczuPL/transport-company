package com.example.transportcompany.model.forms;

import lombok.Data;

@Data
public class AssignVehicleToOrderForm {
    private Long orderId;
    private Long vehicleId;

}
