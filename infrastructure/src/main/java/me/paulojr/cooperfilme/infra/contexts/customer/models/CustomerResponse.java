package me.paulojr.cooperfilme.infra.contexts.customer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerResponse(
        @JsonProperty("customer_name") String name,
        @JsonProperty("customer_email") String email,
        @JsonProperty("customer_phone") String phone
) {
}
