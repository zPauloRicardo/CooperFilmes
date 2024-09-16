package me.paulojr.cooperfilme.infra.contexts.script.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateScriptRequest(
        @JsonProperty("text") String text,
        @JsonProperty("customer_name") String name,
        @JsonProperty("customer_email") String email,
        @JsonProperty("customer_phone") String phone

) {
}
