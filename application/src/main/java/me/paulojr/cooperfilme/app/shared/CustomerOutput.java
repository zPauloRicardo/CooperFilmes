package me.paulojr.cooperfilme.app.shared;

import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;

public record CustomerOutput(
        String name,
        String email,
        String phone
) {


    public static CustomerOutput from(Customer customer) {
        return new CustomerOutput(customer.getName(), customer.getEmail(), customer.getPhone());
    }
}
