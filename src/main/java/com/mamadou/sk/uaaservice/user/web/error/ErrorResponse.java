package com.mamadou.sk.uaaservice.user.web.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;


/**
 * Custom error response that will be return to the client.
 * Sub errors will be returned if multiple errors are expected.
 * The response will be in the following format:
 * {
 *     "status": 400,
 *     "error": "Bad Request",
 *     "description": "Invalid field(s)"
 *     "errors": [
 *          "field": username,
 *          "description": "Username is required"
 *
 *     ]
 * }
 */
@Builder
@Value
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = ErrorResponse.ErrorResponseBuilder.class)
public class ErrorResponse {
    /**
     * http status code
     */
    private final int status;

    /**
     * Http status code name
     */
    private final String error;

    /**
     * Error description
     */
    private final String message;

    /**
     * Sub errors
     */
    @Singular
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private final List<SubErrorResponse> errors;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ErrorResponseBuilder {
    }
}
