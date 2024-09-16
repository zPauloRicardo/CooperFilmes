package me.paulojr.cooperfilme.infra.contexts.script.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddVoteRequest(@JsonProperty("type") String type) {



}
