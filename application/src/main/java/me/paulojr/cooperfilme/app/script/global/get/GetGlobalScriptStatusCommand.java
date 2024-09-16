package me.paulojr.cooperfilme.app.script.global.get;

public record GetGlobalScriptStatusCommand(
        String customerEmail,
        String scriptId,
        Integer page
) {
}
