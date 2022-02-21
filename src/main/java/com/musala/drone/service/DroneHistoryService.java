package com.musala.drone.service;

import com.musala.drone.model.DroneHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneHistoryService {
    void recordHistory();
    Page<DroneHistory> fetchAllHistory(Pageable pageable, String filterBy);

}
