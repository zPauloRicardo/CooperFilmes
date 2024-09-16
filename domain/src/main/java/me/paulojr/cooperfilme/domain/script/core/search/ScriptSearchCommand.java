package me.paulojr.cooperfilme.domain.script.core.search;

import java.util.Date;

public record ScriptSearchCommand(
        Integer page,
        String customerEmail,
        Integer status,
        Date creationDate
) {
}
