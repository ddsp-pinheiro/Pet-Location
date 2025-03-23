package com.pet_location.presentation.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PositionStackError {
    INVALID_ACCESS_KEY("invalid_access_key", "An invalid API access key was supplied.", 401),
    MISSING_ACCESS_KEY("missing_access_key", "No API access key was supplied.", 401),
    INACTIVE_USER("inactive_user", "The given user account is inactive.", 401),
    FUNCTION_ACCESS_RESTRICTED("function_access_restricted", "The given API endpoint is not " +
            "supported on the current subscription plan.", 403),
    INVALID_API_FUNCTION("invalid_api_function", "The given API endpoint does not exist.", 404),
    NOT_FOUND("404_not_found", "Resource not found.", 404),
    USAGE_LIMIT_REACHED("usage_limit_reached", "The given user account has reached its monthly " +
            "allowed request volume.", 429),
    RATE_LIMIT_REACHED("rate_limit_reached", "The given user account has reached the rate limit.", 429),
    INTERNAL_ERROR("internal_error", "Internal server error.", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    public static PositionStackError fromCode(String code) {
        for (PositionStackError error : PositionStackError.values()) {
            if (error.getCode().equals(code)) {
                return error;
            }
        }
        return INTERNAL_ERROR;
    }
}
