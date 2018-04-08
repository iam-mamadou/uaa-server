package com.mamadou.sk.uaaservice.user.web.controller;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UserIdNotFoundException;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.service.UserService;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.error.ErrorResponse;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Rest Controller for user operations.
 * These operations can't only accessed by admin for managing users
 * Here are supported operations:
 *      POST - create new user
 *      PUT - update existing user
 *      DELETE - delete user
 *      GET - get user
 */
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserResource {

    private UserService userService;
    private UserMapper userMapper;

    /**
     * POST /uua/api/v1/users
     *
     * Endpoint for creating a new user.
     * If the username provided already exists an exception is thrown
     * Similarly, if email provided already exists an exception will be thrown.
     *
     * @param newUserDTO - new user to created
     * @return  Response Entity with created status code.
     *          Response Entity with bad request status code is returned
     *          if userId is provided as part of the request
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody @Valid UserDTO newUserDTO) throws URISyntaxException {
        if (newUserDTO.getUserId() != null ) {
            return buildBadRequestResponse("new user can't have id");
        } else {
            User savedUser = userService.createUser(userMapper.toEntity(newUserDTO));
            return ResponseEntity.created(new URI("/api/v1/users/" + savedUser.getUserId()))
                                 .body(userMapper.toDTO(savedUser));
        }
    }

    /**
     * GET /api/v1/users/{userId}
     *
     * Endpoint for finding an existing user by Id
     * An UserIdNotFoundException will be thrown if user does not exists
     *
     * @return UserDTO response entity with OK status code.
     *         else return 404 status code if user is not found
     */
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        User existingUser = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toDTO(existingUser));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return buildBadRequestResponse(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return buildBadRequestResponse(e.getMessage());
    }

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserIdNotFoundException(UserIdNotFoundException e) {
        return buildBadRequestResponse(e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildBadRequestResponse(String message) {
        return ResponseEntity.badRequest()
                             .body(ErrorResponse.builder()
                                                .status(HttpStatus.BAD_REQUEST.value())
                                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                                .message(message)
                                                .build());
    }
}
