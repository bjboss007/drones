package com.musala.drone.repository;

import com.musala.drone.model.DroneHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DroneHistoryRepository extends MongoRepository<DroneHistory, String> {

}
