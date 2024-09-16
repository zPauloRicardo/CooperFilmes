package me.paulojr.cooperfilme.infra.contexts.script.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateScriptResponse(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("email") String email
) {
}
