package com.musala.drone.model.dto;


import com.musala.drone.model.enums.Model;
import lombok.Data;

@Data
public class DroneDTO {
    private double weight;
    private Model model;
    private String serialNumber;
}
