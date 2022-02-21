package com.musala.drone.api;


import com.musala.drone.service.DroneHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drone-history")
public class DroneHistoryController {

    @Autowired
    private DroneHistoryService droneHistoryService;

    @GetMapping
    @ApiOperation(value = "fetchDroneHistory", notes = "You can filter by the drone's serial Number")
    public ResponseEntity<?> fetchDroneHistory(Pageable pageable, @RequestParam(value = "filter", required = false) String filterBy){
        return ResponseEntity.ok(droneHistoryService.fetchAllHistory(pageable, filterBy));
    }
}
