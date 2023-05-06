package com.sichizhande.drone.service;

import com.sichizhande.drone.dto.request.RegisterDroneRequest;
import com.sichizhande.drone.model.Drone;

public interface DroneService {
    Drone register(RegisterDroneRequest registerDroneRequest);

    Drone findById(Long droneId);

    void save(Drone drone);
}
