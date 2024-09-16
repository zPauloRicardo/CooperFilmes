package me.paulojr.cooperfilme.infra.contexts.script.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddStepRequest(@JsonProperty("type") Integer type, @JsonProperty("text") String text) {



}
