package com.mamadou.sk.uaaservice.user.web.error;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {
    private static final String ERROR_DESCRIPTION = "Something very bad happened";
    private ErrorResponse errorResponse;

    @Before
    public void setUp() {
        errorResponse = ErrorResponse.builder()
                                     .status(HttpStatus.BAD_REQUEST.value())
                                     .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                     .message(ERROR_DESCRIPTION)
                                     .build();
    }

    @Test
    public void errorResponse_shouldHave400AsStatus() {
        // then
        assertThat(errorResponse.getStatus()).as("http status code")
                                             .isEqualTo(400);
    }

    @Test
    public void errorResponse_shouldHaveBadRequestAsError() {
        // then
        assertThat(errorResponse.getError()).as("http status code description")
                                             .isEqualTo("Bad Request");
    }

    @Test
    public void errorResponse_shouldHaveErrorMessage() {
        // then
        assertThat(errorResponse.getMessage()).as("Error Description")
                                            .isEqualTo(ERROR_DESCRIPTION);
    }
}