package com.sichizhande.drone.service;

import com.sichizhande.drone.dto.request.RegisterDroneRequest;
import com.sichizhande.drone.dto.response.DroneBatteryResponse;
import com.sichizhande.drone.model.Drone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface DroneService {
    Drone register(RegisterDroneRequest registerDroneRequest);

    Drone findById(Long droneId);

    void save(Drone drone);

    List<Drone> availableDrone();

    DroneBatteryResponse checkBatteryLevel(long droneId);

    Page<Drone> findAll(PageRequest pageRequest);
}
