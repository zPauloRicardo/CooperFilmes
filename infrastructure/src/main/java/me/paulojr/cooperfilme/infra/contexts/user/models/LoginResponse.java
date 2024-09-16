package me.paulojr.cooperfilme.infra.contexts.user.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record LoginResponse(
        @JsonProperty("token") String token,
        @JsonProperty("user") UserResponse user
) {

}