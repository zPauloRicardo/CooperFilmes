package me.paulojr.cooperfilme.infra.contexts.user.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LoginRequest(
        @JsonProperty("email") String email,
        @JsonProperty("password") String password
) {
}
