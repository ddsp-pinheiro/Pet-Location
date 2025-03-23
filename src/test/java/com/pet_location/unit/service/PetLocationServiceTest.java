package com.pet_location.unit.service;

import com.pet_location.application.service.PetLocationService;
import com.pet_location.domain.repository.PetRepository;
import com.pet_location.presentation.exception.SensorNotFoundException;
import com.pet_location.infra.metrics.PetLocationMetrics;
import io.micrometer.core.instrument.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PetLocationServiceTest {

    @Mock
    private PetLocationMetrics petLocationMetrics;

    @Mock
    private Counter mockCounter;

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetLocationService petLocationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(petLocationMetrics.getPetLocationRegistrationCounter()).thenReturn(mockCounter);
    }

    @Test
    void testGetLastLocationBySensorId_WhenNoLocationsExist_ShouldThrowException() {
        String sensorId = "sensor-123";
        when(petRepository.existsBySensorId(sensorId)).thenReturn(true);
        when(petRepository.findLastLocationBySensorId(sensorId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            petLocationService.getLastLocationBySensorId(sensorId);
        });

        assertEquals("Nenhuma localização encontrada para o sensorId: " + sensorId, exception.getMessage());
        verify(petRepository, times(1)).existsBySensorId(sensorId);
        verify(petRepository, times(1)).findLastLocationBySensorId(sensorId);
    }

    @Test
    void testGetPetLocationsBySensorId_WhenNoLocationsExist_ShouldThrowException() {
        String sensorId = "sensor-123";
        when(petRepository.existsBySensorId(sensorId)).thenReturn(true);
        when(petRepository.findLocationsBySensorId(sensorId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            petLocationService.getPetLocationsBySensorId(sensorId);
        });

        assertEquals("Pet não encontrado com sensorId: " + sensorId, exception.getMessage());
        verify(petRepository, times(1)).existsBySensorId(sensorId);
        verify(petRepository, times(1)).findLocationsBySensorId(sensorId);
    }


    @Test
    void testGetLastLocationBySensorId_SensorIdNotFound_ThrowsException() {
        when(petRepository.existsBySensorId("sensor-123")).thenReturn(false);

        assertThrows(SensorNotFoundException.class, () -> {
            petLocationService.getLastLocationBySensorId("sensor-123");
        });
    }
}