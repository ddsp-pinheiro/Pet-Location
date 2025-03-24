package com.pet_location.application.service;

import com.pet_location.domain.model.Location;
import com.pet_location.domain.model.Pet;
import com.pet_location.domain.repository.PetRepository;
import com.pet_location.infra.client.PositionStackClient;
import com.pet_location.presentation.dto.*;
import com.pet_location.presentation.exception.PositionStackError;
import com.pet_location.presentation.exception.PositionStackException;
import com.pet_location.presentation.exception.SensorNotFoundException;
import com.pet_location.infra.metrics.PetLocationMetrics;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class PetLocationService {

    private final PositionStackClient positionStackClient;
    private final PetRepository petRepository;
    private final PetLocationMetrics petLocationMetrics;

    private static final String accessKey = "556cf1add7c0ee0c3ade5d658d0ad754";
    private static final Logger logger = LoggerFactory.getLogger(PetLocationService.class);


    @Transactional
    public PetLocationResponse registerPetLocation(PetLocationRequest request) {
        logger.info("m=registerPetLocation: Iniciando registro de localização para o sensorId: {}",
                request.getSensorId());

        try {
            PositionStackData data = getLocationDetailsFromPositionStack(
                    request.getLatitude(), request.getLongitude()
            );

            Pet pet = findOrCreatePet(request.getSensorId(), request.getLatitude(), request.getLongitude());

            Location location = createLocation(data, pet);
            pet.getLocations().add(location);

            petRepository.save(pet);
            petLocationMetrics.getPetLocationRegistrationCounter().increment();

            PetLocationResponse response = createPetLocationResponse(data);
            logger.info("m=registerPetLocation: Registro de localização concluído com sucesso para o sensorId: {}",
                    request.getSensorId());
            return response;
        } catch (Exception e) {
            logger.error("m=registerPetLocation: Erro ao registrar localização para o sensorId: {}",
                    request.getSensorId(), e);
            throw e;
        }
    }

    private PositionStackData getLocationDetailsFromPositionStack(Double latitude, Double longitude) {
        String query = latitude + "," + longitude;
        logger.info("m=getLocationDetailsFromPositionStack: Chamando API do PositionStack com query: {}", query);

        PositionStackResponse response;
        try {
            response = positionStackClient.getLocationDetails(accessKey, query, "json", 1);
            logger.info("m=getLocationDetailsFromPositionStack: Resposta da API recebida: {}", response);
        } catch (FeignException e) {
            logger.error("m=getLocationDetailsFromPositionStack: Erro ao chamar a API: {}", e.getMessage());
            throw new PositionStackException(PositionStackError.INVALID_ACCESS_KEY);
        }

        if (response.getData() == null || response.getData().length == 0) {
            logger.error("m=getLocationDetailsFromPositionStack: Localização não encontrada para a query: {}", query);
            throw new PositionStackException(PositionStackError.NOT_FOUND);
        }

        return response.getData()[0];
    }

    private Pet findOrCreatePet(String sensorId, Double latitude, Double longitude) {
        return petRepository.findBySensorId(sensorId)
                .map(existingPet -> {
                    logger.info("m=findOrCreatePet: Pet encontrado. Atualizando dados para sensorId: {}", sensorId);
                    existingPet.setLatitude(latitude);
                    existingPet.setLongitude(longitude);
                    existingPet.setDateTime(LocalDateTime.now());
                    return petRepository.save(existingPet);
                })
                .orElseGet(() -> {
                    logger.info("m=findOrCreatePet: Pet não encontrado. Criando novo Pet com sensorId: {}", sensorId);
                    Pet newPet = new Pet();
                    newPet.setSensorId(sensorId);
                    newPet.setLatitude(latitude);
                    newPet.setLongitude(longitude);
                    newPet.setDateTime(LocalDateTime.now());
                    return petRepository.save(newPet);
                });
    }

    private Location createLocation(PositionStackData data, Pet pet) {
        Location location = new Location();
        if (data.getCountry() != null) location.setCountry(data.getCountry());
        if (data.getRegion() != null) location.setState(data.getRegion());
        if (data.getLocality() != null) location.setCity(data.getLocality());
        if (data.getNeighbourhood() != null) location.setNeighborhood(data.getNeighbourhood());
        if (data.getLabel() != null) location.setAddress(data.getLabel());
        location.setPet(pet);

        logger.info("m=createLocation: Location criada: " +
                        "País: {}, Estado: {}, Cidade: {}, Bairro: {}, Endereço: {}",
                location.getCountry(), location.getState(),
                location.getCity(), location.getNeighborhood(),
                location.getAddress());
        return location;
    }

    private PetLocationResponse createPetLocationResponse(PositionStackData data) {
        PetLocationResponse response = new PetLocationResponse(
                data.getCountry(),
                data.getRegion(),
                data.getLocality(),
                data.getNeighbourhood(),
                data.getLabel()
        );

        logger.info("m=createPetLocationResponse: PetLocationResponse criada: {}", response);
        return response;
    }

    private PetLocationResponse createPetLocationResponse(Location location) {
        PetLocationResponse response = new PetLocationResponse(
                location.getCountry(),
                location.getState(),
                location.getCity(),
                location.getNeighborhood(),
                location.getAddress()
        );

        logger.info("m=createPetLocationResponse: PetLocationResponse criada: {}", response);
        return response;
    }

    private List<PetLocationResponse> mapLocationsToResponses(List<Location> locations) {
        return locations.stream()
                .map(this::createPetLocationResponse)
                .collect(Collectors.toList());
    }

    public PetLocationsResponse getPetLocationsBySensorId(String sensorId) {
        verifySensorIdExists(sensorId);

        Pet pet = petRepository.findLocationsBySensorId(sensorId)
                .orElseThrow(() -> {
                    logger.error("m=getPetLocationsBySensorId: Pet não encontrado com sensorId: {}", sensorId);
                    return new RuntimeException("Pet não encontrado com sensorId: " + sensorId);
                });

        logger.info("m=getPetLocationsBySensorId: Pet encontradodo sensor: {}", pet.getSensorId());
        logger.info("m=getPetLocationsBySensorId: Numero de localizações associadas: {}", pet.getLocations().size());

        List<PetLocationResponse> locations = mapLocationsToResponses(pet.getLocations());
        return new PetLocationsResponse(pet.getSensorId(), locations);
    }

    public PetLocationResponse getLastLocationBySensorId(String sensorId) {
        verifySensorIdExists(sensorId);

        Location lastLocation = petRepository.findLastLocationBySensorId(sensorId)
                .orElseThrow(() -> {
                    logger.error("m=getLastLocationBySensorId: " +
                            "Nenhuma localização encontrada para o sensorId: {}", sensorId);
                    return new RuntimeException("Nenhuma localização encontrada para o sensorId: " + sensorId);
                });

        logger.info("m=getLastLocationBySensorId: Detalhes da localização " +
                        "- País: {}, Estado: {}, Cidade: {}, Bairro: {}, Endereço: {}",
                lastLocation.getCountry(),
                lastLocation.getState(),
                lastLocation.getCity(),
                lastLocation.getNeighborhood(),
                lastLocation.getAddress());

        return createPetLocationResponse(lastLocation);
    }

    private void verifySensorIdExists(String sensorId) {
        logger.info("m=verifySensorIdExists: Verificando existência do sensorId: {}", sensorId);
        if (!petRepository.existsBySensorId(sensorId)) {
            logger.error("m=verifySensorIdExists: SensorId não encontrado: {}", sensorId);
            throw new SensorNotFoundException("SensorId não encontrado: " + sensorId);
        }
    }
}