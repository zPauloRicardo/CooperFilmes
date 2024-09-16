package me.paulojr.cooperfilme.domain.script.core.entity;

import me.paulojr.cooperfilme.domain.shared.domain.Identifier;
import me.paulojr.cooperfilme.domain.shared.utils.UUIDUtils;

import java.util.Objects;
import java.util.UUID;

/**
 * Representa um identificador único para um Script.
 * Herda da classe genérica {@link Identifier} com {@link UUID} como tipo de identificação.
 */
public class ScriptID extends Identifier<UUID> {

    private final UUID uuid;

    /**
     * Construtor para criar um ScriptID com um UUID específico.
     *
     * @param uuid o UUID associado ao identificador do Script.
     */
    public ScriptID(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Cria um novo ScriptID com um UUID gerado automaticamente.
     *
     * @return um novo ScriptID com um UUID único.
     */
    public static ScriptID unique() {
        return ScriptID.from(UUIDUtils.uuid());
    }

    /**
     * Cria um ScriptID a partir de uma string que representa um UUID.
     *
     * @param anId a string que representa o UUID.
     * @return um ScriptID correspondente ao UUID representado pela string.
     */
    public static ScriptID from(final String anId) {
        return new ScriptID(UUIDUtils.getFromString(anId));
    }

    /**
     * Retorna o valor do identificador como um UUID.
     *
     * @return o UUID associado ao ScriptID.
     */
    @Override
    public UUID getValue() {
        return this.uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScriptID userID = (ScriptID) o;
        return Objects.equals(uuid, userID.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
