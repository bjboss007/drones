package com.musala.drone.api;


import com.musala.drone.commons.AppResponse;
import com.musala.drone.model.Drone;
import com.musala.drone.model.Medication;
import com.musala.drone.model.assembler.DroneAssembler;
import com.musala.drone.model.dto.DroneDTO;
import com.musala.drone.model.dto.MedicationDTO;
import com.musala.drone.model.dto.MedicationListDTO;
import com.musala.drone.model.enums.Status;
import com.musala.drone.service.DroneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @Autowired
    private DroneAssembler droneAssembler;

    @GetMapping
    public ResponseEntity<?> fetchAllDrones(){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneAssembler.toCollectionModel(droneService.fetchAllDrones()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public  ResponseEntity<?> registerDrone(@RequestBody @Valid DroneDTO droneDTO){
        Drone drone = droneService.registerDrone(droneDTO);
        EntityModel<Drone> entityModel = droneAssembler.toModel(drone);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @GetMapping("/{droneId}/medications")
    @ApiOperation(value = "fetchMedicationList", notes = "Drone Id can either be the drone's id or serial Number")
    public ResponseEntity<?> fetchMedicationList(@PathVariable("droneId") String droneId){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneService.fetchDroneMedicationsWithId(droneId))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available")
    public ResponseEntity<?> fetchAvailableDrone(){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneService.availableDroneForLoading())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{droneId}")
    @ApiOperation(value = "fetchDroneWithDroneId", notes = "Drone Id can either be the drone's id or serial Number")
    public ResponseEntity<?> fetchDroneWithDroneId(@PathVariable("droneId") String droneId){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneAssembler.toModel(droneService.fetchDroneById(droneId)))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{droneId}/battery-level")
    public ResponseEntity<?> getDroneBatteryLevel(@PathVariable("droneId") String droneId) {
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneService.fetchDroneById(droneId).getBatteryPercentage())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{droneId}/load-drone")
    public ResponseEntity<?> loadDrone(@PathVariable("droneId") String droneId, @Valid @RequestBody MedicationListDTO medicationList){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneAssembler.toModel(droneService.loadDrone(droneId, medicationList)))
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("update-status")
    public ResponseEntity<?> updateDroneStatus(@RequestParam("droneId") String droneId, @RequestParam Status status){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneAssembler.toModel(droneService.updateStatus(droneId, status)))
                .build();

        return ResponseEntity.ok(response);
    }

    @PatchMapping("update-status")
    public ResponseEntity<?> rechargeDrone(@RequestParam("droneId") String droneId, @RequestParam("batterylevel") int level){
        AppResponse response = AppResponse.builder()
                .message("success")
                .data(droneAssembler.toModel(droneService.updateBatteryLevel(droneId, level)))
                .build();

        return ResponseEntity.ok(response);
    }

}
