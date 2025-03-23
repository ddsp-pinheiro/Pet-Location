package com.pet_location.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Getter
@Schema(description = "Resposta com os detalhes da localização do pet")
public class PetLocationResponse {

    @Schema(description = "País da localização", example = "Brazil", examples = {})
    private String country;

    @Schema(description = "Estado da localização", example = "São Paulo")
    private String state;

    @Schema(description = "Cidade da localização", example = "São Paulo")
    private String city;

    @Schema(description = "Bairro da localização", example = "Centro")
    private String neighborhood;

    @Schema(description = "Endereço completo da localização", example = "Av. Paulista, 1000")
    private String address;

}