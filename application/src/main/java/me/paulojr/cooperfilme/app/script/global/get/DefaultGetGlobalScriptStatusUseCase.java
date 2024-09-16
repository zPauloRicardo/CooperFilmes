package me.paulojr.cooperfilme.app.script.global.get;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.script.customer.gateway.CustomerGateway;
import me.paulojr.cooperfilme.domain.shared.exceptions.NotFoundException;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.shared.validation.handler.Notification;

import java.util.List;
import java.util.function.Supplier;

public class DefaultGetGlobalScriptStatusUseCase extends GetGlobalScriptStatusUseCase {

    private final ScriptGateway scriptGateway;
    private final CustomerGateway customerGateway;

    public DefaultGetGlobalScriptStatusUseCase(ScriptGateway scriptGateway, CustomerGateway customerGateway) {
        this.scriptGateway = scriptGateway;
        this.customerGateway = customerGateway;
    }


    @Override
    public Pagination<GetGlobalScriptStatusOutput> execute(GetGlobalScriptStatusCommand anIn) {

        final Notification errors = Notification.create();
        if ((anIn.customerEmail() == null || anIn.customerEmail().isBlank())) {
            errors.append("Email não pode ser nulo ou vazio.");
        }
        errors.throwIfHasError();

        final Customer customer = this.customerGateway.findByEmail(anIn.customerEmail()).orElseThrow(notFound(anIn.customerEmail()));

        if(anIn.scriptId() != null && !anIn.scriptId().isBlank()) {
            final ScriptID scriptID = ScriptID.from(anIn.scriptId());
            final Script script = this.scriptGateway.findById(scriptID).orElseThrow(notFound(scriptID));
            return Pagination.of(1, 1, 1, List.of(GetGlobalScriptStatusOutput.from(script)));
        }

        return this.scriptGateway.findByCustomerId(customer.getId(), anIn.page()).map(GetGlobalScriptStatusOutput::from);
    }

    private Supplier<NotFoundException> notFound(final String email) {
        return () -> NotFoundException.with(new Error(String.format("Cliente com email %s não foi encontrado.", email)));
    }


    private Supplier<NotFoundException> notFound(final ScriptID id) {
        return () -> NotFoundException.with(Script.class, id);
    }



}
