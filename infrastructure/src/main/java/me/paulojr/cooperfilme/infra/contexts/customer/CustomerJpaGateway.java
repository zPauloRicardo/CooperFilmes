package me.paulojr.cooperfilme.infra.contexts.customer;

import lombok.RequiredArgsConstructor;
import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.script.customer.entity.CustomerID;
import me.paulojr.cooperfilme.domain.script.customer.gateway.CustomerGateway;
import me.paulojr.cooperfilme.infra.contexts.customer.persistence.CustomerJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.customer.persistence.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class CustomerJpaGateway implements CustomerGateway {

    private final CustomerRepository customerRepository;


    @Override
    public Optional<Customer> findByEmail(String anEmail) {
        return customerRepository.findByEmail(anEmail).map(CustomerJpaEntity::toDomain);
    }


    @Override
    public Customer create(Customer aCustomer) {
        return this.customerRepository.save(CustomerJpaEntity.from(aCustomer)).toDomain();
    }

    @Override
    public Optional<Customer> findById(CustomerID aCustomerID) {
        return this.customerRepository.findById(aCustomerID.getValue()).map(CustomerJpaEntity::toDomain);
    }


    @Override
    public Customer update(Customer aCustomer) {
        return this.customerRepository.save(CustomerJpaEntity.from(aCustomer)).toDomain();
    }
}
