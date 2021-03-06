package com.mamadou.sk.uaaservice.user.web.controller;

import com.mamadou.sk.uaaservice.user.entitity.User;
import com.mamadou.sk.uaaservice.user.exception.EmailAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.exception.UserNotFoundException;
import com.mamadou.sk.uaaservice.user.exception.UsernameAlreadyExistsException;
import com.mamadou.sk.uaaservice.user.service.UserService;
import com.mamadou.sk.uaaservice.user.web.dto.UserDTO;
import com.mamadou.sk.uaaservice.user.web.error.ErrorResponse;
import com.mamadou.sk.uaaservice.user.web.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            return buildErrorResponse("new user can't have id", HttpStatus.BAD_REQUEST);
        } else {
            User savedUser = userService.createUser(userMapper.toEntity(newUserDTO));
            return ResponseEntity.created(new URI("/api/v1/users/" + savedUser.getUserId()))
                                 .body(userMapper.toDTO(savedUser));
        }
    }

    /**
     * GET /api/v1/users?page={pageNumber}&size={pageSize}&sort={propertyName},{asc|desc}&sort={propertyName},{asc|desc}
     *
     * Endpoint for retrieving all available users
     * By default 20 users are returned per page. To change this value you can set the size param
     * This is zero-based page index i.e the first page is 0
     * If the sort criteria specified users will be ordered by userID in an ascending order
     * fields can be sorted as follows:
     *  sort=firstName&sort=username,asc
     *
     * @param pageable - pagination information
     * @return Page of UserDTOs Response Entity with OK status code
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers(Pageable pageable)  {
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(userMapper.toDTOPage(users));
    }

    /**
     * GET /api/v1/users/{userId}
     *
     * Endpoint for finding an existing user by Id
     * UserNotFoundException will be thrown if user does not exists
     *
     * @return UserDTO response entity with 200 status code.
     *         else return 404 status code if user is not found
     */
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        User existingUser = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toDTO(existingUser));
    }

    /**
     * PUT /api/v1/users/{userId}
     *
     * Endpoint for updating an existing user by id
     * UserNotFoundException will be thrown if user is not associated with the given id
     *
     * @param userId - existing user id
     * @param userDTO - user that needs to be updates
     * @return UserDTO response entity with 200 status code
     *                else return 404 status if user is not found
     */
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@PathVariable Long userId, @RequestBody @Valid UserDTO userDTO) {
        User updatedUser = userService.updateUser(userId, userMapper.toEntity(userDTO));
        return ResponseEntity.ok(userMapper.toDTO(updatedUser));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserIdNotFoundException(UserNotFoundException e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder() // response body
                                                 .status(httpStatus.value())
                                                 .error(httpStatus.getReasonPhrase())
                                                 .message(message)
                                                 .build(),
                                    httpStatus);
    }
}
