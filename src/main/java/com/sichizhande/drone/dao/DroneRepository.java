package com.sichizhande.drone.dao;

import com.sichizhande.drone.enums.State;
import com.sichizhande.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAllByStateInAndBatteryCapacityIsGreaterThan(Collection<State> state, double batteryCapacity);
}
