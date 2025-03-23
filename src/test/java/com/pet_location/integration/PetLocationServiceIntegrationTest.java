package com.pet_location.integration;

import com.pet_location.application.service.PetLocationService;
import com.pet_location.domain.model.Pet;
import com.pet_location.domain.repository.PetRepository;
import com.pet_location.presentation.dto.PetLocationRequest;
import com.pet_location.presentation.dto.PetLocationResponse;
import com.pet_location.presentation.dto.PetLocationsResponse;
import com.pet_location.presentation.exception.SensorNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PetLocationServiceIntegrationTest {

    @Autowired
    private PetLocationService petLocationService;

    @Autowired
    private PetRepository petRepository;


    private static final String SENSOR_ID = "sensor-123";
    private static final double LATITUDE_1 = -23.5505;
    private static final double LONGITUDE_1 = -46.6333;
    private static final String DATE_TIME_1 = "2024-01-01T12:00:00";

    private static final double LATITUDE_2 = -23.5510;
    private static final double LONGITUDE_2 = -46.6340;
    private static final String DATE_TIME_2 = "2024-01-01T12:05:00";

    private static final String COUNTRY = "Brazil";
    private static final String STATE = "Sao Paulo";
    private static final String CITY = "São Paulo";
    private static final String NEIGHBORHOOD = "Se";
    private static final String ADDRESS = "Praca Da SE SN, São Paulo, Brazil";

    @Test
    @Transactional
    void testRegisterPetLocation_Integration() {
        PetLocationRequest request = createPetLocationRequest(SENSOR_ID, LATITUDE_1, LONGITUDE_1, DATE_TIME_1);

        PetLocationResponse response = petLocationService.registerPetLocation(request);

        assertNotNull(response);
        assertEquals(COUNTRY, response.getCountry());

        Optional<Pet> savedPet = petRepository.findBySensorId(SENSOR_ID);
        assertTrue(savedPet.isPresent());
        assertEquals(1, savedPet.get().getLocations().size());
    }

    @Test
    @Transactional
    void testGetPetLocationsBySensorId_ShouldReturnAllLocations() {
        registerPetLocation(SENSOR_ID, LATITUDE_1, LONGITUDE_1, DATE_TIME_1);
        registerPetLocation(SENSOR_ID, LATITUDE_2, LONGITUDE_2, DATE_TIME_2);

        PetLocationsResponse response = petLocationService.getPetLocationsBySensorId(SENSOR_ID);

        assertNotNull(response);
        assertEquals(SENSOR_ID, response.getSensorId());
        assertEquals(2, response.getLocations().size());
    }

    @Test
    @Transactional
    void testGetLastLocationBySensorId_ShouldReturnMostRecentLocation() {
        registerPetLocation(SENSOR_ID, LATITUDE_1, LONGITUDE_1, DATE_TIME_1);
        registerPetLocation(SENSOR_ID, LATITUDE_2, LONGITUDE_2, DATE_TIME_2);

        PetLocationResponse response = petLocationService.getLastLocationBySensorId(SENSOR_ID);

        assertNotNull(response);
        assertEquals(COUNTRY, response.getCountry());
        assertEquals(STATE, response.getState());
        assertEquals(CITY, response.getCity());
        assertEquals(NEIGHBORHOOD, response.getNeighborhood());
        assertEquals(ADDRESS, response.getAddress());
    }

    @Test
    @Transactional
    void testGetPetLocationsBySensorId_WhenSensorIdDoesNotExist_ShouldThrowException() {
        String sensorId = "sensor-inexistente";

        SensorNotFoundException exception = assertThrows(SensorNotFoundException.class, () -> {
            petLocationService.getPetLocationsBySensorId(sensorId);
        });

        assertEquals("SensorId não encontrado: " + sensorId, exception.getMessage());
    }

    private PetLocationRequest createPetLocationRequest(String sensorId, double latitude, double longitude, String dateTime) {
        PetLocationRequest request = new PetLocationRequest();
        request.setSensorId(sensorId);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setDateTime(dateTime);
        return request;
    }

    private void registerPetLocation(String sensorId, double latitude, double longitude, String dateTime) {
        PetLocationRequest request = createPetLocationRequest(sensorId, latitude, longitude, dateTime);
        petLocationService.registerPetLocation(request);
    }
}