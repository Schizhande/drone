package com.sichizhande.drone.service;

import com.sichizhande.drone.dto.request.LoadDroneRequest;
import com.sichizhande.drone.model.Medication;

public interface MedicationService {
    Medication loadDrone(LoadDroneRequest loadDroneRequest);
}
