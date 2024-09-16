package me.paulojr.cooperfilme.domain.shared.utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class InstantUtils {

    private InstantUtils() {
    }

    /**
     * Retorna o instante atual com precisão até microssegundos.
     *
     * @return o instante atual truncado para microssegundos.
     */
    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MICROS);
    }
}
