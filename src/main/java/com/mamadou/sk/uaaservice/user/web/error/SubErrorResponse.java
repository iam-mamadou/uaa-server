package com.mamadou.sk.uaaservice.user.web.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Sub error response that will be returned as part of ErrorResponse
 * if multiple errors are founds.
 */
@Getter
@Setter
@AllArgsConstructor
public class SubErrorResponse {
    /**
     * field name
     */
    private String field;

    /**
     * error description
     */
    private String description;
}
