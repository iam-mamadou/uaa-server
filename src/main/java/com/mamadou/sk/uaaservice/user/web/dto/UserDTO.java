package com.mamadou.sk.uaaservice.user.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Value
@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
public class UserDTO {

    private Long userId;

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username field must be between 6-30 characters long")
    private String username;

    @Email
    @NotEmpty(message = "Email address is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 4, message = "Password must be greater than 8 characters long")
    private String password;

    @Singular
    private List<AuthorityDTO> authorities;


    private  boolean enabled;

    private boolean locked;

    private boolean expired;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserDTOBuilder {

    }
}
