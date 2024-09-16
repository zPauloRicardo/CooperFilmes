package me.paulojr.cooperfilme.infra.contexts.script.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetScriptResponse(
        @JsonProperty("id") String id,
        @JsonProperty("status") String status,
        @JsonProperty("justification") String justification
) {
}
