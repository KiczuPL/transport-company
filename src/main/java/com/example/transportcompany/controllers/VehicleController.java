package com.example.transportcompany.controllers;

import com.example.transportcompany.model.dao.Vehicle;
import com.example.transportcompany.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/save")
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody Vehicle vehicle) {
        return new ResponseEntity<Vehicle>(vehicleService.saveVehicle(vehicle), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return new ResponseEntity<Vehicle>(vehicleService.getVehicle(id), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Vehicle> getByCompany(@RequestParam(required = true) String registrationNumber) {
        return new ResponseEntity<Vehicle>(vehicleService.findByRegistrationNumber(registrationNumber), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle(@Validated @RequestBody Vehicle vehicle) {
        return new ResponseEntity<Vehicle>(vehicleService.updateVehicle(vehicle), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Vehicle> deleteVehicle(@RequestBody Long id) {
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<Vehicle>(HttpStatus.OK);
    }


}
