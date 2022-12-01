package com.example.transportcompany.model.dto;

import com.example.transportcompany.model.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CreateOrderForm {

    @NotNull
    @NotBlank
    private String addressFrom;
    @NotNull
    @NotBlank
    private String addressTo;
    @NotNull
    @NotNull
    private Long companyId;
    @NotNull
    private LocalDate pickUpDate;
    @NotNull
    private VehicleType vehicleType;

}
