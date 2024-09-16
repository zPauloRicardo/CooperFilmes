package me.paulojr.cooperfilme.app.script.global.create;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.vo.StepVo;

public record CreateScriptOutput(
        String id,
        String status,
        String email
) {


    public static CreateScriptOutput from(Script script) {
        final StepVo step = script.getLastStep();
        return new CreateScriptOutput(script.getId().getValue().toString(), step.type().getDescription(), script.getCustomer().getEmail());
    }
}
