package com.sichizhande.drone.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sichizhande.drone.dao.DroneRepository;
import com.sichizhande.drone.dto.request.RegisterDroneRequest;
import com.sichizhande.drone.model.Drone;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

    private final ObjectMapper objectMapper;

    private final DroneRepository droneRepository;

    public DroneServiceImpl(ObjectMapper objectMapper, DroneRepository droneRepository) {
        this.objectMapper = objectMapper;
        this.droneRepository = droneRepository;
    }

    @Override
    public Drone register(RegisterDroneRequest registerDroneRequest) {

        val drone = objectMapper.convertValue(registerDroneRequest, Drone.class);

        return droneRepository.save(drone);
    }
}
