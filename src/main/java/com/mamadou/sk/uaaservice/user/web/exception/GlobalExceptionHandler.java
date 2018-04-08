package com.mamadou.sk.uaaservice.user.web.exception;
import com.mamadou.sk.uaaservice.user.web.error.ErrorResponse;
import com.mamadou.sk.uaaservice.user.web.error.SubErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

/**
 * Global Exception handling
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * handle all unhandled Exceptions. this the exception is
     * not handle this will be returned to the client
     *
     * @return Error Response entity with internal server status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> fallbackException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(ErrorResponse.builder()
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                                .message("The server encountered unexpected error")
                                                .build());
    }

    /**
     * handle invalid argument exceptions
     *
     * @param e - MethodArgumentNotValidException
     * @return ErrorResponse response entity including the associated sub errors
     *         with bad request status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponse.builder()
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .message("Invalid field(s)")
                                                .errors(toSubErrorResponse(bindingResult.getFieldErrors()))
                                                .build());
    }

    /**
     * handle mismatch method argument type
     * @param e - MethodArgumentTypeMismatchException
     * @return Error Response entity with bad request status code
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String me = format("{0} should be a type {1}", e.getName(), e.getRequiredType().getSimpleName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponse.builder()
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .message(me)
                                                .build());
    }

    private List<SubErrorResponse> toSubErrorResponse(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                          .map(fieldError -> new SubErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                          .collect(Collectors.toList());
    }


}
