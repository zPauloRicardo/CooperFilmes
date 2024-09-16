package me.paulojr.cooperfilme.app.script.secured.step;

public record AddStepCommand(
        String scriptId,
        String userID,
        Integer type,
        String text
) {
}
