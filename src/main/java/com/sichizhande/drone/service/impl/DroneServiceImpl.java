package com.sichizhande.drone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sichizhande.drone.dao.DroneRepository;
import com.sichizhande.drone.dto.request.RegisterDroneRequest;
import com.sichizhande.drone.exceptions.RecordNotFoundException;
import com.sichizhande.drone.model.Drone;
import com.sichizhande.drone.service.DroneService;
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

    @Override
    public Drone findById(Long droneId) {
        return droneRepository.findById(droneId)
                .orElseThrow(() -> new RecordNotFoundException("Drone with id " + droneId + " not found"));
    }

    @Override
    public void save(Drone drone) {
        droneRepository.save(drone);
    }
}
