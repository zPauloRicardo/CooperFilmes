package me.paulojr.cooperfilme.infra.contexts.customer.presenters;

import me.paulojr.cooperfilme.app.shared.CustomerOutput;
import me.paulojr.cooperfilme.infra.contexts.customer.models.CustomerResponse;

public interface CustomerApiPresenter {

    static CustomerResponse present(CustomerOutput output) {
        if (output == null) return null;
        return new CustomerResponse(
                output.name(),
                output.email(),
                output.phone()
        );
    }

}
