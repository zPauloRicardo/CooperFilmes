package me.paulojr.cooperfilme.app.script.secured.search;

import me.paulojr.cooperfilme.app.UseCase;
import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.search.ScriptSearchCommand;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;

public abstract class SearchScriptUseCase extends UseCase<ScriptSearchCommand, Pagination<ScriptOutput>> {
}
