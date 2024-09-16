package me.paulojr.cooperfilme.app.script.secured.vote;

public record VoteCommand(
        String scriptId,
        String userID,
        String type
) {
}
