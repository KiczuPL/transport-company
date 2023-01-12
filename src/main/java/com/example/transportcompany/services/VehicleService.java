package com.example.transportcompany.services;


import com.example.transportcompany.model.VehicleType;
import com.example.transportcompany.model.dao.Order;
import com.example.transportcompany.model.dao.Vehicle;
import com.example.transportcompany.model.dto.OrderDto;
import com.example.transportcompany.model.forms.CreateVehicleForm;
import com.example.transportcompany.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @RolesAllowed("ROLE_ADMIN")
    public Vehicle saveVehicle(CreateVehicleForm form) {
        Vehicle toSave = new Vehicle(form.getVehicleIdentifier(),form.getRegistrationNumber(),form.getType());
        log.info("Saving vehicle: {}", toSave.toString());
        return vehicleRepository.save(toSave);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Vehicle getVehicle(Long id) {
        return vehicleRepository.getReferenceById(id);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Map<String, Object> getVehicles(String registrationNumber,
                                           String vehicleIdentifier,
                                           VehicleType type,
                                           Integer page,
                                           Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("vehicleIdentifier"));
        log.info("Handling get vehicles request registrationNumber={} vehicleIdentifier={} type={} page={} size={}", registrationNumber, vehicleIdentifier, type, page, size);

        VehicleType typeFrom = VehicleType.values()[0];
        VehicleType typeTo = VehicleType.values()[VehicleType.values().length - 1];
        if (type != null) {
            typeFrom = type;
            typeTo = type;
        }

        Page<Vehicle> pageOrder = vehicleRepository.findAllByRegistrationNumberContainingIgnoreCaseAndVehicleIdentifierContainingIgnoreCaseAndTypeGreaterThanEqualAndTypeLessThanEqual(registrationNumber, vehicleIdentifier, typeFrom, typeTo, pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("vehicles", pageOrder.getContent().stream());
        response.put("currentPage", pageOrder.getNumber());
        response.put("totalItems", pageOrder.getTotalElements());
        response.put("totalPages", pageOrder.getTotalPages());
        return response;
    }

    @RolesAllowed("ROLE_ADMIN")
    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle vehicleById;
        try {
            vehicleById = vehicleRepository.getReferenceById(vehicle.getId());
        } catch (Exception exception) {
            throw new NoSuchElementException("No such vehicle exists in repository");
        }
        if(vehicleRepository.findByVehicleIdentifier(vehicle.getVehicleIdentifier()) != null || vehicleRepository.findByRegistrationNumber(vehicle.getRegistrationNumber())!=null)
            throw new IllegalArgumentException("There already exists vehicle with that id or registration number in repository");

        log.info("Updating vehicle: {}  to: {}", vehicleById.toString(), vehicle.toString());
        return vehicleRepository.save(vehicle);
    }

    @RolesAllowed("ROLE_ADMIN")
    public Vehicle findByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    @RolesAllowed("ROLE_ADMIN")
    public void deleteVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.getReferenceById(id);
        log.info("Deleting vehicle: {}", vehicle.toString());
        vehicleRepository.deleteById(id);
    }

}
