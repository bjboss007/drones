package com.musala.drone.repository;

import com.musala.drone.model.Drone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends MongoRepository<Drone, String> {
}
