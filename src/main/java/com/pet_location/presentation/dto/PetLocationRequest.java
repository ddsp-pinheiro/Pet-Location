package com.pet_location.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Requisição com coordenadas e ID do snesor")
public class PetLocationRequest {
    @NotBlank(message = "O sensorId é obrigatório")
    @Schema(description = "ID do sensor", example = "sensor123" )
    private String sensorId;

    @NotNull(message = "A latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "A latitude deve ser maior ou igual a -90")
    @DecimalMax(value = "90.0", message = "A latitude deve ser menor ou igual a 90")
    @Schema(description = "Latitude", example = "90" )
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "A longitude deve ser maior ou igual a -180")
    @DecimalMax(value = "180.0", message = "A longitude deve ser menor ou igual a 180")
    @Schema(description = "Latitude", example = "180" )
    private Double longitude;

    @NotBlank(message = "A data/hora é obrigatória")
    @Schema(description = "Data e hora", example = "2023-10-01T12:00:00" )
    private String dateTime;
}
