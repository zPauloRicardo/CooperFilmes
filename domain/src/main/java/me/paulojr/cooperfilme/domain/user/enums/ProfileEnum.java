package me.paulojr.cooperfilme.domain.user.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * Enumeração que representa os diferentes perfis de usuário no sistema.
 * Cada perfil tem uma chave (key) e uma descrição associada.
 */
public enum ProfileEnum {

    /**
     * Perfil de analista.
     */
    ANALISTA("A","Analista"),
    /**
     * Perfil de revisor.
     */
    REVISOR("R","Revisor"),
    /**
     * Perfil de aprovador.
     */
    APROVADOR("P","Aprovador");


    /**
     * Construtor para inicializar o perfil com uma chave e uma descrição.
     *
     * @param key a chave associada ao perfil.
     * @param description a descrição do perfil.
     */
    ProfileEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }


    @Getter
    private final String key;

    @Getter
    private final String description;

    public static ProfileEnum getFromKey(String key) {
        return Arrays.stream(values()).filter(a -> a.getKey().equals(key)).findFirst().orElse(ANALISTA);
    }
}
