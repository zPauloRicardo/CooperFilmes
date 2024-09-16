package me.paulojr.cooperfilme.app.shared;

import me.paulojr.cooperfilme.domain.script.core.vo.StepVo;

import java.util.Date;

public record StepOutput(
        String type,
        String justification,
        UserOutput userOutput,
        Date stepDate
) {


    public static StepOutput from (StepVo stepVo) {
        final UserOutput userOutput = stepVo.userId() != null ? new UserOutput(stepVo.userId().getValue().toString(), stepVo.userName()) : null;
        return new StepOutput(stepVo.type().getDescription(), stepVo.justification(), userOutput, stepVo.stepDate());
    }
}
