package com.pet_location.integration;

import com.pet_location.presentation.dto.PositionStackResponse;
import com.pet_location.infra.client.PositionStackClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PositionStackClientIntegrationTest {

    @Autowired
    private PositionStackClient positionStackClient;

    @Test
    void testGetLocationDetails_WithRealApi_ShouldReturnData() {
        PositionStackResponse response = positionStackClient.getLocationDetails(
                "f78554794f26bad1ccf06352d58e3395",
                "-23.5505,-46.6333",
                "json", 1);

        assertNotNull(response);
        assertTrue(response.getData().length > 0);
        assertEquals("Brazil", response.getData()[0].getCountry());
    }
}
