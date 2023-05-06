package com.sichizhande.drone.service;

import com.sichizhande.drone.dto.request.LoadDroneRequest;
import com.sichizhande.drone.model.Medication;

import java.util.List;

public interface MedicationService {
    Medication loadDrone(LoadDroneRequest loadDroneRequest);

    List<Medication> findMedicationByDroneId(long droneId);
}
