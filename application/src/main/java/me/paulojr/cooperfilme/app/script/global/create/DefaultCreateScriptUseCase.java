package me.paulojr.cooperfilme.app.script.global.create;

import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.script.customer.gateway.CustomerGateway;
import me.paulojr.cooperfilme.domain.shared.validation.handler.Notification;

import java.util.Optional;

public class DefaultCreateScriptUseCase extends CreateScriptUseCase {

    private final ScriptGateway scriptGateway;
    private final CustomerGateway customerGateway;

    public DefaultCreateScriptUseCase(ScriptGateway scriptGateway, CustomerGateway customerGateway) {
        this.scriptGateway = scriptGateway;
        this.customerGateway = customerGateway;
    }


    @Override
    public CreateScriptOutput execute(CreateScriptCommand anIn) {


        final Notification errors = Notification.create();
        if (anIn.customerEmail() == null || anIn.customerEmail().isBlank()) {
            errors.append("Email não pode ser nulo ou vazio.");
        }
        errors.throwIfHasError();

        final Customer customer = this.createOrUpdateCustomer(anIn, errors);

        final Script script = Script.newScript(customer, anIn.text());
        script.validate(errors);
        errors.throwIfHasError();


        return CreateScriptOutput.from(this.scriptGateway.create(script));
    }

    private Customer createOrUpdateCustomer(CreateScriptCommand anIn, Notification errors) {
        final Optional<Customer> customerOptional = this.customerGateway.findByEmail(anIn.customerEmail());

        Customer customer;

        if (customerOptional.isPresent()) {
            customer = customerOptional.get();
            final Customer updatedCustomer = Customer.with(
                    customer.getId(),
                    anIn.customerName(),
                    anIn.customerEmail(),
                    anIn.customerPhone(),
                    customer.getCreationDate()
            );

            if (!customer.equals(updatedCustomer)) {
                updatedCustomer.validate(errors);
                errors.throwIfHasError();
            }

            customer = this.customerGateway.update(updatedCustomer);
        } else {
            customer = Customer.newCustomer(anIn.customerName(), anIn.customerEmail(), anIn.customerPhone());
            customer.validate(errors);
            errors.throwIfHasError();
            customer = this.customerGateway.create(customer);
        }
        return customer;
    }


}
