package com.musala.drone.service.implementation;

import com.musala.drone.commons.exceptions.BadRequestException;
import com.musala.drone.commons.exceptions.ObjectNotFoundException;
import com.musala.drone.model.Drone;
import com.musala.drone.model.Medication;
import com.musala.drone.model.dto.DroneDTO;
import com.musala.drone.model.enums.Status;
import com.musala.drone.repository.DroneRepository;
import com.musala.drone.service.DroneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Drone> fetchAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone registerDrone(DroneDTO droneDTO) {
        Drone drone = modelMapper.map(droneDTO, Drone.class);
        if (droneRepository.existsBySerialNumber(drone.getSerialNumber())){
            throw new BadRequestException("Drone already exists with the serial Number");
        }
        return droneRepository.save(drone);
    }

    @Override
    public Drone fetchDroneById(String droneId) {
        return droneRepository.findByDroneId(droneId)
                .orElseThrow(() -> new ObjectNotFoundException("Drone not found with Id or Serial Number " + droneId));
    }

    @Override
    public List<Medication> fetchDroneMedicationsWithId(String droneId) {
        return fetchDroneById(droneId).getMedicationList();
    }

    @Override
    public List<Drone> availableDroneForLoading() {
        return droneRepository.findDronesByStatus(Status.LOADING);
    }
}
