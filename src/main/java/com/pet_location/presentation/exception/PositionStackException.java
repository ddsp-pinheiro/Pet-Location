package com.pet_location.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PositionStackException extends RuntimeException {
    private final PositionStackError error;
}
