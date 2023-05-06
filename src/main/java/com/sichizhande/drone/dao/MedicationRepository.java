package com.sichizhande.drone.dao;

import com.sichizhande.drone.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    @Query(value = "select COALESCE(sum(weight),0) from medication where drone_id =?1", nativeQuery = true)
    double calculateTotalDroneWeight(Long id);
}
