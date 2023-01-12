package com.example.transportcompany.repositories;

import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Vehicle findByRegistrationNumber(String registrationNumber);
    Vehicle findByVehicleIdentifier(String vehicleIdentifier);
    Page<Vehicle> findAllByRegistrationNumberContainingIgnoreCaseAndVehicleIdentifierContainingIgnoreCaseAndTypeGreaterThanEqualAndTypeLessThanEqual(String registrationNumber, String vehicleIdentifier, VehicleType vehicleTypeFrom, VehicleType vehicleTypeTo, Pageable pageable);
}
