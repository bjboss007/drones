package com.musala.drone.service;

import com.musala.drone.model.Drone;
import com.musala.drone.model.Medication;
import com.musala.drone.model.dto.DroneDTO;
import com.musala.drone.model.dto.MedicationDTO;
import com.musala.drone.model.dto.MedicationListDTO;
import com.musala.drone.model.enums.Status;

import java.util.List;

public interface DroneService {

    List<Drone> fetchAllDrones();
    Drone registerDrone(DroneDTO droneDTO);
    Drone fetchDroneById(String droneId);
    List<Medication> fetchDroneMedicationsWithId(String droneId);
    List<Drone> availableDroneForLoading();
//    Drone loadDrone(String drone, List<MedicationDTO> medicationList);
    Drone loadDrone(String drone, MedicationListDTO medicationList);
    Drone updateStatus(String droneId, Status status);

}
