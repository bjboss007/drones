package com.musala.drone.api;


import com.musala.drone.service.DroneHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drone-history")
public class DroneHistoryController {

    @Autowired
    private DroneHistoryService droneHistoryService;

    @GetMapping
    public ResponseEntity<?> fetchDroneHistory(Pageable pageable){
        return ResponseEntity.ok(droneHistoryService.fetchAllHistory(pageable));
    }
}
