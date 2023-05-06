package com.sichizhande.drone.dao;

import com.sichizhande.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
