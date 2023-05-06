package com.sichizhande.drone.api;


import com.sichizhande.drone.dto.request.LoadDroneRequest;
import com.sichizhande.drone.model.Medication;
import com.sichizhande.drone.service.MedicationService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class MedicationRestController {

    private final MedicationService medicationService;

    public MedicationRestController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping(value = "/drones/{droneId}/medications", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Medication loadDrone(@PathVariable long droneId, @Valid @ModelAttribute LoadDroneRequest loadDroneRequest) {
        loadDroneRequest.setDroneId(droneId);
        return medicationService.loadDrone(loadDroneRequest);
    }
}
