package me.paulojr.cooperfilme.domain.script.customer.entity;

import me.paulojr.cooperfilme.domain.script.customer.validation.CustomerValidator;
import me.paulojr.cooperfilme.domain.shared.domain.AggregateRoot;
import me.paulojr.cooperfilme.domain.shared.utils.InstantUtils;
import me.paulojr.cooperfilme.domain.shared.validation.ValidationHandler;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;


/**
 * Representa um cliente no sistema.
 */
@Getter
public class Customer extends AggregateRoot<CustomerID> {

    private String name;
    private String email;
    private String phone;
    private Date creationDate;


    /**
     * Construtor protegido para {@link Customer}.
     *
     * @param customerID ID do cliente.
     * @param name Nome do cliente.
     * @param email E-mail do cliente.
     * @param phone Telefone do cliente.
     * @param creationDate Data de criação do cliente.
     */
    protected Customer(CustomerID customerID, String name, String email, String phone, Date creationDate) {
        super(customerID);
        this.name = name;
        this.email = email != null ? email.toLowerCase() : null;
        this.phone = phone;
        this.creationDate = creationDate;
    }


    /**
     * Cria uma nova instância de {@link Customer} com um ID gerado automaticamente e a data atual.
     *
     * @param name Nome do cliente.
     * @param email E-mail do cliente.
     * @param phone Telefone do cliente.
     * @return Nova instância de {@link Customer}.
     */
    public static Customer newCustomer(String name, String email, String phone) {
        final Date now = Date.from(InstantUtils.now());
        return new Customer(null, name, email, phone, now);
    }


    /**
     * Cria uma instância de {@link Customer} com ID, nome, e-mail, telefone e data de criação fornecidos.
     *
     * @param customerID ID do cliente.
     * @param name Nome do cliente.
     * @param email E-mail do cliente.
     * @param phone Telefone do cliente.
     * @param creationDate Data de criação do cliente.
     * @return Instância de {@link Customer}.
     */
    public static Customer with(CustomerID customerID, String name, String email, String phone, Date creationDate){
        return new Customer(customerID, name, email, phone, creationDate);
    }


    /**
     * Valida o cliente usando o {@link CustomerValidator}.
     *
     * @param handler Manipulador de validação.
     */
    @Override
    public void validate(ValidationHandler handler) {
        new CustomerValidator(handler, this).validate();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(phone, customer.phone) && Objects.equals(creationDate, customer.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, email, phone, creationDate);
    }
}
