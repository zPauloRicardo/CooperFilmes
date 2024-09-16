package me.paulojr.cooperfilme.app.script.secured.search;

import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.script.core.search.ScriptSearchCommand;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;

public class DefaultSearchScriptUseCase extends SearchScriptUseCase {

    private final ScriptGateway scriptGateway;

    public DefaultSearchScriptUseCase(ScriptGateway scriptGateway) {
        this.scriptGateway = scriptGateway;
    }


    @Override
    public Pagination<ScriptOutput> execute(ScriptSearchCommand anIn) {
        return scriptGateway.search(anIn).map(ScriptOutput::from);
    }


}
