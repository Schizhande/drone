package com.sichizhande.drone.tasks;

import com.sichizhande.drone.model.Drone;
import com.sichizhande.drone.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DroneCronJob {

    private final DroneService droneService;

    private static final int BATCH_SIZE = 20;

    public DroneCronJob(DroneService droneService) {
        this.droneService = droneService;
    }

    @Scheduled(cron = "0 */5 * * * *") //every 5 minute
    public void cron() {

        int page = 0;

        Page<Drone> drones;

        do {
            drones = droneService.findAll(PageRequest.of(page, BATCH_SIZE));

            auditLog(drones);

            page++;
        } while (drones.hasNext());
    }

    private void auditLog(Page<Drone> drones) {
        drones.getContent()
                .forEach(drone -> log.info("---> Drone serial number : [{}] battery level [{}]", drone.getSerialNumber(),
                        drone.getBatteryCapacity()));
    }
}
