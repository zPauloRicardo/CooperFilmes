package me.paulojr.cooperfilme.app.shared;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;

import java.util.List;

public record ScriptOutput(

        String id,
        String text,
        CustomerOutput customerOutput,
        StepOutput lastStep,
        List<StepOutput> steps,
        List<VoteOutput> votes

) {

    public static ScriptOutput from(Script script) {
        return new ScriptOutput(
                script.getId().getValue().toString(),
                script.getText(),
                CustomerOutput.from(script.getCustomer()),
                StepOutput.from(script.getLastStep()),
                script.getSteps().stream().map(StepOutput::from).toList(),
                script.getVotes().stream().map(VoteOutput::from).toList()
        );
    }

}
