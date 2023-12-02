package com.jme.shareride.service.vehicleServices;

import com.jme.shareride.requests.transport.vehicle.VehicleRegRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface VehicleService {

    ResponseEntity addVehicle(HttpServletRequest request,VehicleRegRequest vReg);
    ResponseEntity findAllVehicles(Pageable pageable);

    ResponseEntity findVehicleByCategory(HttpServletRequest servletRequest,  String  categoryName, Pageable pageable);

    ResponseEntity findVehicleById(long id);
}
