package com.example.transportcompany.services;


import com.example.transportcompany.model.dao.Vehicle;
import com.example.transportcompany.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        log.info("Saving vehicle: {}", vehicle.toString());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicle(Long id) {
        return vehicleRepository.getReferenceById(id);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        try {
            Vehicle vehicleById = vehicleRepository.getReferenceById(vehicle.getId());
            log.info("Updating vehicle: {}  to: {}", vehicleById.toString(), vehicle.toString());
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Long id) {
        vehicleRepository.deleteById(id);
    }

}
