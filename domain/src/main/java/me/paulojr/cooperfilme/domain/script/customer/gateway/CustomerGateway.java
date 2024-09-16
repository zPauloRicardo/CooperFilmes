package me.paulojr.cooperfilme.domain.script.customer.gateway;

import me.paulojr.cooperfilme.domain.script.customer.entity.Customer;
import me.paulojr.cooperfilme.domain.script.customer.entity.CustomerID;

import java.util.Optional;

public interface CustomerGateway {

    /**
     * Busca um cliente pelo seu email.
     *
     * <p>Este método recebe um email e retorna um
     * {@link Optional} contendo o cliente correspondente, se encontrado.
     *
     * @param anEmail o email do cliente.
     * @return um {@link Optional} contendo o cliente, se encontrado; caso contrário, um Optional vazio.
     */
    Optional<Customer> findByEmail(final String anEmail);

    /**
     * Busca um cliente pelo seu identificador único.
     *
     * <p>Este método recebe um identificador de cliente e retorna um
     * {@link Optional} contendo o cliente correspondente, se encontrado.
     *
     * @param anCustomerId o identificador único do cliente.
     * @return um {@link Optional} contendo o cliente, se encontrado; caso contrário, um Optional vazio.
     */
    Optional<Customer> findById(final CustomerID anCustomerId);

    /**
     * Cria um novo cliente no sistema.
     *
     * <p>Este método recebe um objeto {@link Customer} contendo os dados
     * do cliente a ser salvo e persiste esses dados no banco de dados.
     *
     * @param toSave o objeto {@link Customer} contendo as informações do cliente a ser criado.
     * @return o objeto {@link Customer} após ser salvo, contendo o ID e quaisquer outras informações geradas.
     */
    Customer create(final Customer toSave);

    /**
     * Atualiza um cliente existente no sistema.
     *
     * <p>Este método recebe um objeto {@link Customer} com as informações
     * atualizadas e realiza a atualização correspondente no banco de dados.
     *
     * @param toUpdate o objeto {@link Customer} contendo as informações atualizadas do cliente.
     * @return o objeto {@link Customer} após ser atualizado.
     */
    Customer update(final Customer toUpdate);

}
