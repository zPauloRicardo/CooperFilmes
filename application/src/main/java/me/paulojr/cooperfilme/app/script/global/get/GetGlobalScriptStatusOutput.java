package me.paulojr.cooperfilme.app.script.global.get;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.vo.StepVo;

public record GetGlobalScriptStatusOutput(
        String id,
        String status,
        String justification
) {


    public static GetGlobalScriptStatusOutput from(Script script) {
        final StepVo step = script.getLastStep();
        return new GetGlobalScriptStatusOutput(script.getId().getValue().toString(), step.type().getDescription(), step.justification());
    }
}
