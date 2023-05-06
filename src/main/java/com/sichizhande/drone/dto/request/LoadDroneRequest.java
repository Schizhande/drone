package com.sichizhande.drone.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LoadDroneRequest {

    @JsonIgnore
    private Long droneId;

    @Pattern(regexp = "^[A-Za-z0-9-_]*$", message = "Invalid name provided")
    private String name;

    private double weight;

    @Pattern(regexp = "^[A-Z0-9_]*$", message = "Invalid code provided")
    private String code;

    private MultipartFile imageFile;
}
