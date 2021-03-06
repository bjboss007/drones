package com.musala.drone.service.implementation;

import com.cloudinary.utils.ObjectUtils;
import com.musala.drone.commons.exceptions.BadRequestException;
import com.musala.drone.commons.exceptions.ObjectNotFoundException;
import com.musala.drone.config.CloudinaryConfig;
import com.musala.drone.model.Drone;
import com.musala.drone.model.Medication;
import com.musala.drone.model.dto.DroneDTO;
import com.musala.drone.model.dto.MedicationDTO;
import com.musala.drone.model.dto.MedicationListDTO;
import com.musala.drone.model.enums.Status;
import com.musala.drone.repository.DroneRepository;
import com.musala.drone.service.DroneService;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryConfig config;


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

    @Override
    public Drone updateBatteryLevel(String droneId, int batteryLevel) {
        Drone drone = fetchDroneById(droneId);
        int totalBatteryLevel = drone.getBatteryPercentage() + batteryLevel;
        if(drone.getBatteryPercentage() < 100 & totalBatteryLevel <= 100){
            drone.setBatteryPercentage(totalBatteryLevel);
            droneRepository.save(drone);
        }else {
            throw new BadRequestException("Battery level is above 100 percent!");
        }
        return drone;
    }

    @Override
    public Drone loadDrone(String droneId, MedicationListDTO medicationList) {
        Drone drone = fetchDroneById(droneId);

        if(drone.getBatteryPercentage() < 25) throw new BadRequestException("Drone's battery level is below 25%");

        double droneExistingWeight = drone.getMedicationList()
                .stream().mapToDouble(Medication::getWeight)
                .sum();

        double incomingWeight = medicationList.getMedicationList().stream().mapToDouble(MedicationDTO::getWeight).sum();

        double sum = droneExistingWeight + incomingWeight;
        if(sum > drone.getWeight()) throw new BadRequestException("Incoming weight is more than the Drone's capacity");

        medicationList.getMedicationList().stream()
                        .map(medication -> {
                            if(medication.getImage() != null && !medication.getImage().isEmpty()) {
                                Map upload = config.upload(medication.getImage(), ObjectUtils.asMap("resourcetype", "auto"));
                                String url = upload.get("url").toString();
                                medication.setImage(url);
                            }
                            return medication;
                        })
                .map(m -> modelMapper.map(m, Medication.class))
                .forEach(drone::addMedication);

        drone.setStatus(Status.LOADED);

        return droneRepository.save(drone);
    }

    @Override
    public Drone updateStatus(String droneId, Status status) {
        Drone drone = fetchDroneById(droneId);
        if(drone.getBatteryPercentage() < 25 && status.equals(Status.LOADING)) throw new BadRequestException("Drone's battery level is below 25%");
        if(status.equals(Status.DELIVERED)){
            Integer percentage = drone.getBatteryPercentage();
            drone.setBatteryPercentage(percentage - 25);
        }
        drone.setStatus(status);
        return droneRepository.save(drone);
    }


}
