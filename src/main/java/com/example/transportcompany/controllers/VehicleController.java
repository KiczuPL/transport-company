package com.example.transportcompany.controllers;

import com.example.transportcompany.email.EmailServiceImpl;
import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Vehicle;
import com.example.transportcompany.model.forms.CreateVehicleForm;
import com.example.transportcompany.services.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping("/save")
    public ResponseEntity<Vehicle> saveVehicle(@RequestBody CreateVehicleForm form) {
        return new ResponseEntity<Vehicle>(vehicleService.saveVehicle(form), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return new ResponseEntity<Vehicle>(vehicleService.getVehicle(id), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> getVehicles(@RequestParam(required = false) String registrationNumber,
                                                           @RequestParam(required = false) String vehicleIdentifier,
                                                           @RequestParam(required = false) VehicleType type,
                                                           @RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return new ResponseEntity<Map<String, Object>>(vehicleService.getVehicles(registrationNumber, vehicleIdentifier, type, page, size), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Vehicle> updateVehicle(@Validated @RequestBody Vehicle vehicle) {
        return new ResponseEntity<Vehicle>(vehicleService.updateVehicle(vehicle), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Vehicle> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
        return new ResponseEntity<Vehicle>(HttpStatus.OK);
    }

    private final EmailServiceImpl emailService;

    @GetMapping("/test")
    public void test(){
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        //emailService.sendSimpleMessage("KiczuPL@outlook.com","łohou!","xD");
    }


}
