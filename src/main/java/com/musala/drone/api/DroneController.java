package com.musala.drone.api;


import com.musala.drone.commons.AppResponse;
import com.musala.drone.model.Drone;
import com.musala.drone.model.assembler.DroneAssembler;
import com.musala.drone.model.dto.DroneDTO;
import com.musala.drone.service.DroneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public  ResponseEntity<?> registerDrone(@RequestBody DroneDTO droneDTO){
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

}
