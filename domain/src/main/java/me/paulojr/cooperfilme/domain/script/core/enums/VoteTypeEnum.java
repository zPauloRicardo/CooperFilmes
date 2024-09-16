package me.paulojr.cooperfilme.domain.script.core.enums;

import lombok.Getter;

import java.util.Arrays;


/**
 * Enumeração que representa os tipos de voto.
 *
 * <p>Cada valor da enumeração contém uma chave e uma descrição associadas
 * ao tipo de voto, como "Aprovado", "Reprovado" e "Erro" (para casos onde
 * o tipo de voto não foi encontrado).
 */
@Getter
public enum VoteTypeEnum {
    /**
     * Voto aprovado.
     */
    APROVADO("A", "Aprovado"),

    /**
     * Voto reprovado.
     */
    REPROVADO("R", "Reprovado"),

    /**
     * Tipo de voto não encontrado (erro).
     */
    ERRO("E", "Não encontrado tipo de voto");


    /**
     * Construtor para a enumeração {@link VoteTypeEnum}.
     *
     * @param key a chave única que identifica o tipo de voto.
     * @param description a descrição do tipo de voto.
     */
    VoteTypeEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    private final String key;
    private final String description;


    /**
     * Obtém o tipo de voto correspondente à chave fornecida.
     *
     * <p>Este método realiza uma busca pelo valor da chave fornecida
     * ignorando diferenças de maiúsculas e minúsculas. Se não encontrar
     * um tipo correspondente, retorna {@link VoteTypeEnum#ERRO}.
     *
     * @param key a chave do tipo de voto.
     * @return o tipo de voto correspondente à chave, ou {@link VoteTypeEnum#ERRO} se não encontrado.
     */
    public static VoteTypeEnum getByKey(String key) {
        return Arrays.stream(values()).filter(type -> type.getKey().equalsIgnoreCase(key)).findFirst().orElse(ERRO);
    }



}
