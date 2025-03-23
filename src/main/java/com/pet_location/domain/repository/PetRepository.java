package com.pet_location.domain.repository;

import com.pet_location.domain.model.Location;
import com.pet_location.domain.model.Pet;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findBySensorId(String sensorId);

    boolean existsBySensorId(String sensorId);

    @Query("SELECT p FROM PET p LEFT JOIN FETCH p.locations WHERE p.sensorId = :sensorId")
    Optional<Pet> findLocationsBySensorId(@Param("sensorId") String sensorId);

    @Query("SELECT l FROM LOCATION l WHERE l.pet.id = " +
            "(SELECT p.id FROM PET p WHERE p.sensorId = :sensorId ORDER BY p.dateTime DESC LIMIT 1) " +
            "ORDER BY l.id DESC LIMIT 1")
    Optional<Location> findLastLocationBySensorId(@Param("sensorId") String sensorId);

}
