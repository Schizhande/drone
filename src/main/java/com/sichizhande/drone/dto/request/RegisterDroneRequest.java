package com.sichizhande.drone.dto.request;

import com.sichizhande.drone.enums.Model;
import com.sichizhande.drone.enums.State;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDroneRequest {

    @Size(max = 200, message = "{validation.name.size.too_long}")
    private String serialNumber;

    private Model model;

    @Max(value = 500, message = "Weight limit should not exceed 500")
    private int weightLimit;

    @Max(value = 100, message = "Battery capacity should not exceed 100%")
    private double batteryCapacity;

    private State state;
}
