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
 *     "description": "Username already exists"
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
    private int status;

    /**
     * Http status code name
     */
    private String error;

    /**
     * Error description
     */
    private String message;

    /**
     * Sub errors
     */
    @Singular
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<SubErrorResponse> errors = new ArrayList<>();

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ErrorResponseBuilder { }
}
