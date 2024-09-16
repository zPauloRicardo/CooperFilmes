package me.paulojr.cooperfilme.domain.script.customer.entity;

import me.paulojr.cooperfilme.domain.shared.domain.Identifier;
import me.paulojr.cooperfilme.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;


/**
 * Representa o identificador de um cliente.
 */
public class CustomerID extends Identifier<UUID> {

    private final UUID uuid;


    /**
     * Construtor para {@link CustomerID}.
     *
     * @param uuid UUID do identificador.
     */
    protected CustomerID(UUID uuid) {
        this.uuid = uuid;
    }


    /**
     * Cria um novo identificador {@link CustomerID} com um UUID gerado automaticamente.
     *
     * @return Novo {@link CustomerID}.
     */
    public static CustomerID unique() {
        return CustomerID.from(UUIDUtils.uuid());
    }

    /**
     * Cria um {@link CustomerID} a partir de uma representação em String do UUID.
     *
     * @param anId Representação em String do UUID.
     * @return {@link CustomerID} correspondente.
     */

    public static CustomerID from(final String anId) {
        return new CustomerID(UUIDUtils.getFromString(anId));
    }


    /**
     * Obtém o valor do identificador UUID.
     *
     * @return UUID do identificador.
     */
    @Override
    public UUID getValue() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerID userID = (CustomerID) o;
        return Objects.equals(uuid, userID.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
