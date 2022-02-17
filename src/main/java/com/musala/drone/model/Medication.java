package com.musala.drone.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medication {
    private String name;
    private double weight;
    private String code;
    private String image;
}
