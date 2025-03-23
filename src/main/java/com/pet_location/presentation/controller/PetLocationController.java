package com.pet_location.presentation.controller;

import com.pet_location.presentation.dto.PetLocationRequest;
import com.pet_location.presentation.dto.PetLocationResponse;
import com.pet_location.presentation.dto.PetLocationsResponse;
import com.pet_location.presentation.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.pet_location.application.service.PetLocationService;

@RestController
@RequestMapping("/api/pet-location")
@Tag(name = "Pet Location", description = "Endpoints para localização de pets")
public class PetLocationController {

    private final PetLocationService petLocationService;

    public PetLocationController(PetLocationService petLocationService) {
        this.petLocationService = petLocationService;
    }

    @PostMapping
    @Operation(summary = "Registrar localização do pet",
            description = "Recebe as coordenadas do pet e retorna a localização completa")
    @ApiResponse(responseCode = "200", description = "Localização registrada com sucesso",
            content = @Content(schema = @Schema(implementation = PetLocationResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public PetLocationResponse registerPetLocation(@Valid @RequestBody PetLocationRequest request) {
        return petLocationService.registerPetLocation(request);
    }

    @GetMapping("/{sensorId}/locations")
    @Operation(summary = "Obter localizações do pet",
            description = "Retorna todas as localizações registradas para um sensor específico")
    @ApiResponse(responseCode = "200", description = "Localizações encontradas",
            content = @Content(schema = @Schema(implementation = PetLocationsResponse.class)))
    @ApiResponse(responseCode = "404", description = "Sensor não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public PetLocationsResponse getPetLocations(
            @Parameter(name = "sensorId", example = "sensor123", description = "ID do sensor")
            @PathVariable("sensorId") String sensorId) {
        return petLocationService.getPetLocationsBySensorId(sensorId);
    }

    @GetMapping("/{sensorId}/last-location")
    @Operation(summary = "Obter a última localização do pet",
            description = "Retorna a última localização registrada para o pet com o sensorId especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Última localização encontrada",
                    content = @Content(schema = @Schema(implementation = PetLocationResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sensor não encontrado",
                    content = @Content(schema = @Schema(implementation = RuntimeException.class), examples ={} )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public PetLocationResponse getLastLocation(
            @Parameter(name = "sensorId", example = "sensor123", description = "ID do sensor")
            @PathVariable("sensorId") String sensorId) {
        return petLocationService.getLastLocationBySensorId(sensorId);
    }
}
