package com.musala.drone.model;


import com.musala.drone.model.enums.Model;
import com.musala.drone.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {
    @Id
    private String id;
    private double weight;
    @Builder.Default
    private String serialNumber = UUID.randomUUID().toString();
    @Builder.Default
    private Integer batteryPercentage = 100;
    private Model model;
    @Builder.Default
    private Status status = Status.IDLE;
    private List<Medication> medicationList = new ArrayList<>();

    private void addMedication(Medication medication){
        if (medicationList != null) this.medicationList.add(medication);
    }
}
