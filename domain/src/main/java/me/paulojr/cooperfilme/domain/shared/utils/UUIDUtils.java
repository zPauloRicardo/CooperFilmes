package me.paulojr.cooperfilme.domain.shared.utils;

import me.paulojr.cooperfilme.domain.shared.exceptions.DomainException;
import me.paulojr.cooperfilme.domain.shared.exceptions.InternalErrorException;

import java.util.UUID;

public class UUIDUtils {
    private UUIDUtils() {
    }

    /**
     * Converte uma representação em string de UUID para um objeto {@link UUID}.
     *
     * @param uuidStr a string que representa o UUID.
     * @return o objeto {@link UUID} correspondente.
     * @throws DomainException se a string fornecida não for uma representação válida de UUID.
     */
    public static UUID getFromString(String uuidStr) {
        final UUID uuid;
        try {
            uuid = UUID.fromString(uuidStr);
        } catch (Exception ex) {
            throw InternalErrorException.with("UUID inválida", ex);
        }

        return uuid;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }


    /**
     * Verifica se a string fornecida é uma representação válida de UUID.
     *
     * @param uuid a string que representa o UUID a ser verificado.
     * @return {@code true} se a string for uma representação válida de UUID; {@code false} caso contrário.
     */
    public static boolean isValidUUID(String uuid) {
        try {
            getFromString(uuid);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
