package com.pet_location.infra.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PetLocationMetrics {

    private static final Logger logger = LoggerFactory.getLogger(PetLocationMetrics.class);
    private final Counter petLocationRegistrationCounter;

    public PetLocationMetrics(MeterRegistry meterRegistry) {
        this.petLocationRegistrationCounter = Counter.builder("pet.location.registration.count")
                .description("Número de registros de localização de pets")
                .register(meterRegistry);
        logger.info("m=PetLocationMetrics: Counter 'pet.location.registration.count' registrado com sucesso.");
    }

}