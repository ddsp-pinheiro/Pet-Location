package com.pet_location.infra.client;

import com.pet_location.presentation.dto.PositionStackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "positionStackClient", url = "http://api.positionstack.com/v1")
public interface PositionStackClient {

    @GetMapping("/reverse")
    PositionStackResponse getLocationDetails(
            @RequestParam("access_key") String accessKey,
            @RequestParam("query") String query,
            @RequestParam("output") String output,
            @RequestParam("limit") int limit
    );
}
