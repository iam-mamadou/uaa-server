package com.mamadou.sk.uaaservice.user.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@JsonDeserialize(builder = UserDTO.UserDTOBuilder.class)
@Builder
@Value
public class UserDTO {

    private Long userId;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    @Singular
    private List<AuthorityDTO> authorities;

    private String password;

    private  boolean enabled;

    private boolean locked;

    private boolean expired;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserDTOBuilder {

    }
}
