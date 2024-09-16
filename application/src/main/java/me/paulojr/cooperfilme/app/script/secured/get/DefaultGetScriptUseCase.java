package me.paulojr.cooperfilme.app.script.secured.get;

import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.shared.exceptions.NotFoundException;

import java.util.function.Supplier;

public class DefaultGetScriptUseCase extends GetScriptUseCase {

    private final ScriptGateway scriptGateway;

    public DefaultGetScriptUseCase(ScriptGateway scriptGateway) {
        this.scriptGateway = scriptGateway;
    }

    private Supplier<NotFoundException> notFound(final ScriptID scriptId) {
        return () -> NotFoundException.with(Script.class, scriptId);
    }



    @Override
    public ScriptOutput execute(GetScriptCommand anIn) {
        final ScriptID scriptID = ScriptID.from(anIn.scriptId());

        final Script script = this.scriptGateway.findById(scriptID).orElseThrow(notFound(scriptID));

        return ScriptOutput.from(script);
    }
}
