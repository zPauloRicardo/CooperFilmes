package me.paulojr.cooperfilme.infra.contexts.customer.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.script.customer.entity.CustomerID;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cf_clientes")
@NoArgsConstructor
@Getter
public class CustomerJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "data_cadastro")
    private Date dataCadastro;

    public CustomerJpaEntity(UUID id) {
        this.id = id;
    }

    public CustomerJpaEntity(UUID id, String nome, String email, String telefone, Date dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
    }

    public static CustomerJpaEntity from(Customer customer) {
        if (customer == null) return null;
        return new CustomerJpaEntity(
                customer.getId() != null ? customer.getId().getValue() : null,
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getCreationDate()
        );
    }

    public Customer toDomain() {
        return Customer.with(
                CustomerID.from(this.id.toString()),
                this.getNome(),
                this.getEmail(),
                this.getTelefone(),
                this.getDataCadastro()
        );
    }
}
