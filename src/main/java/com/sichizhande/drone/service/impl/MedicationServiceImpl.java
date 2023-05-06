package com.sichizhande.drone.service.impl;

import com.sichizhande.drone.dao.MedicationRepository;
import com.sichizhande.drone.dto.request.LoadDroneRequest;
import com.sichizhande.drone.exceptions.InvalidRequestException;
import com.sichizhande.drone.model.Drone;
import com.sichizhande.drone.model.Medication;
import com.sichizhande.drone.service.DroneService;
import com.sichizhande.drone.service.FileStorageService;
import com.sichizhande.drone.service.MedicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sichizhande.drone.enums.State.*;

@Slf4j
@Service
public class MedicationServiceImpl implements MedicationService {

    private final DroneService droneService;

    private final FileStorageService fileStorageService;

    private final MedicationRepository medicationRepository;

    public MedicationServiceImpl(DroneService droneService, FileStorageService fileStorageService,
                                 MedicationRepository medicationRepository) {
        this.droneService = droneService;
        this.fileStorageService = fileStorageService;
        this.medicationRepository = medicationRepository;
    }

    @Override
    public Medication loadDrone(LoadDroneRequest loadDroneRequest) {

        log.info("---> Load drone {}", loadDroneRequest);
        Drone drone = droneService.findById(loadDroneRequest.getDroneId());

        double totalWeight = validateLoadDrone(drone, loadDroneRequest);

        Medication medication = new Medication();
        medication.setName(loadDroneRequest.getName());
        medication.setCode(loadDroneRequest.getCode());
        medication.setWeight(loadDroneRequest.getWeight());
        medication.setDrone(drone);

        String fileName = fileStorageService.storeFile(loadDroneRequest.getImageFile());
        medication.setImage(fileName);

        if (drone.getState() == IDLE) {
            drone.setState(LOADING);
        }

        if (drone.getWeightLimit() == totalWeight) {
            drone.setState(LOADED);
        }

        droneService.save(drone);

        return medicationRepository.save(medication);
    }

    private double validateLoadDrone(Drone drone, LoadDroneRequest loadDroneRequest) {

        if (drone.getState() != LOADING && drone.getState() != IDLE) {
            throw new InvalidRequestException("You cannot load a drone that is not in LOADING or IDLE state");
        }
        if (drone.getState() == LOADING && drone.getBatteryCapacity() < 25) {
            throw new InvalidRequestException("Battery level is below 25%");
        }

        double totalWeight = medicationRepository.calculateTotalDroneWeight(drone.getId());

        double newTotalWeight = totalWeight + loadDroneRequest.getWeight();

        if (newTotalWeight > drone.getWeightLimit()) {
            throw new InvalidRequestException("Total weight exceed weight limit of " + drone.getWeightLimit());
        }

        return newTotalWeight;

    }
}
