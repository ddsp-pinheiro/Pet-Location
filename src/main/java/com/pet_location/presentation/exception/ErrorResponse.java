package com.pet_location.presentation.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> details;

    public ErrorResponse(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}