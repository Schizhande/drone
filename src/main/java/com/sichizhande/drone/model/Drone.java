package com.sichizhande.drone.model;

import com.sichizhande.drone.enums.Model;
import com.sichizhande.drone.enums.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Entity
@Table(name = "drone")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "model")
    private Model model;

    @Column(name = "weight_limit")
    private int weightLimit;

    @Column(name = "battery_capacity")
    private double batteryCapacity;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;
}
