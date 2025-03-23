package com.pet_location.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet_location.presentation.dto.PetLocationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PetLocationContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterPetLocation_InvalidSensorId_ShouldReturnBadRequest() throws Exception {
        PetLocationRequest request = new PetLocationRequest();
        request.setSensorId("");
        request.setLatitude(-23.5505);
        request.setLongitude(-46.6333);
        request.setDateTime("2024-01-01T12:00:00");

        mockMvc.perform(post("/api/pet-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterPetLocation_InvalidLatitude_ShouldReturnBadRequest() throws Exception {
        PetLocationRequest request = new PetLocationRequest();
        request.setSensorId("sensor-123");
        request.setLatitude(-100.0);
        request.setLongitude(-46.6333);
        request.setDateTime("2024-01-01T12:00:00");

        mockMvc.perform(post("/api/pet-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterPetLocation_InvalidLongitude_ShouldReturnBadRequest() throws Exception {
        PetLocationRequest request = new PetLocationRequest();
        request.setSensorId("sensor-123");
        request.setLatitude(-23.5505);
        request.setLongitude(-200.0);
        request.setDateTime("2024-01-01T12:00:00");

        mockMvc.perform(post("/api/pet-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterPetLocation_InvalidDateTime_ShouldReturnBadRequest() throws Exception {
        PetLocationRequest request = new PetLocationRequest();
        request.setSensorId("sensor-123");
        request.setLatitude(-23.5505);
        request.setLongitude(-46.6333);
        request.setDateTime("");

        mockMvc.perform(post("/api/pet-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}