package me.paulojr.cooperfilme.infra.contexts.user.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;

@Builder
public record UserResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("profile") String profile,
        @JsonProperty("email") String email,
        @JsonProperty("registration_date") Date registrationDate
) {

}
