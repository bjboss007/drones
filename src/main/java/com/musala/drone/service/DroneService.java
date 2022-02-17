package com.musala.drone.service;

import com.musala.drone.model.Drone;
import com.musala.drone.model.Medication;
import com.musala.drone.model.dto.DroneDTO;

import java.util.List;

public interface DroneService {

    List<Drone> fetchAllDrones();
    Drone registerDrone(DroneDTO droneDTO);
    Drone fetchDroneById(String droneId);
    List<Medication> fetchDroneMedicationsWithId(String droneId);
    List<Drone> availableDroneForLoading();
}
