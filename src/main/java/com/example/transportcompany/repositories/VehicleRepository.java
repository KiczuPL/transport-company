package com.example.transportcompany.repositories;

import com.example.transportcompany.model.dao.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
