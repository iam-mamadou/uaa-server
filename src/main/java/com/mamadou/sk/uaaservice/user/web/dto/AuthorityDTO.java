package com.mamadou.sk.uaaservice.user.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = AuthorityDTO.AuthorityDTOBuilder.class)
@Builder
@Value
public class AuthorityDTO {

    private String role;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AuthorityDTOBuilder {

    }

}
