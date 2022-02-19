package com.musala.drone.model.dto;


import com.mongodb.lang.NonNullApi;
import com.musala.drone.model.enums.Model;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DroneDTO {
    @Min(value = 0, message = "Please provide weight greater zero")
    private double weight;
    private Model model;
    private String serialNumber;
}
