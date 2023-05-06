package com.sichizhande.drone.api;

import com.sichizhande.drone.dto.request.RegisterDroneRequest;
import com.sichizhande.drone.model.Drone;
import com.sichizhande.drone.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneRestController {

    private final DroneService droneService;

    public DroneRestController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public Drone register(@Valid @RequestBody RegisterDroneRequest registerDroneRequest) {
        return droneService.register(registerDroneRequest);
    }
}
