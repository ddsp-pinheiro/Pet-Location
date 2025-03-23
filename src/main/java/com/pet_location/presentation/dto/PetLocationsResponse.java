package com.pet_location.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Resposta com todas as localizações atreladas ao sensor do pet")
public class PetLocationsResponse {
    @Schema(description = "ID do sensor", example = "sensor123" )
    private String sensorId;
    private List<PetLocationResponse> locations;

    public PetLocationsResponse(String sensorId, List<PetLocationResponse> locations) {
        this.sensorId = sensorId;
        this.locations = locations;
    }
}
