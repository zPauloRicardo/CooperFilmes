package me.paulojr.cooperfilme.app.script.global.create;

public record CreateScriptCommand(
        String customerName,
        String customerPhone,
        String customerEmail,
        String text
) {
}
