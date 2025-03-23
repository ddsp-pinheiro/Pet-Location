package com.pet_location.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet_location.presentation.exception.PositionStackError;
import com.pet_location.presentation.exception.PositionStackException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorCode = extractErrorCode(response);
        logger.error("Erro na chamada à API: {}", errorCode);
        PositionStackError error = PositionStackError.fromCode(errorCode);
        return new PositionStackException(error);
    }

    private String extractErrorCode(Response response) {
        try (InputStream body = response.body().asInputStream()) {
            Map errorResponse = objectMapper.readValue(body, Map.class);

            if (errorResponse.containsKey("error")) {
                return (String) errorResponse.get("error");
            } else if (errorResponse.containsKey("code")) {
                return (String) errorResponse.get("code");
            }
            if (response.body() == null) {
                return "internal_error";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "internal_error";
    }
}
