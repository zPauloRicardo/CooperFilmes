package me.paulojr.cooperfilme.domain.script.core.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enumeração que representa os diferentes tipos de etapas no processo de análise e aprovação.
 *
 * <p>Cada valor da enumeração contém uma chave única, uma descrição, os possíveis próximos
 * passos que podem ser tomados, e flags que indicam se a etapa permite votação e se é um status final.
 */
public enum StepTypeEnum {

    /**
     * Etapa onde o processo está aguardando análise.
     */
    AGUARDANDO_ANALISE(1, "Aguardando Analise", Set.of(2)),

    /**
     * Etapa onde o processo está sendo analisado.
     */
    EM_ANALISE(2, "Em Analise", Set.of(3, 8)),

    /**
     * Etapa onde o processo está aguardando revisão.
     */
    AGUARDANDO_REVISAO(3, "Aguardando Revisão", Set.of(4)),

    /**
     * Etapa onde o processo está sendo revisado.
     */
    EM_REVISAO(4, "Em Revisão", Set.of(5)),

    /**
     * Etapa onde o processo está aguardando aprovação.
     */
    AGUARDANDO_APROVACAO(5, "Aguardando Aprovação", Set.of(6, 8), true, false),

    /**
     * Etapa onde o processo está sendo aprovado.
     */
    EM_APROVACAO(6, "Em Aprovação", Set.of(7, 8), true, false),

    /**
     * Etapa onde o processo foi aprovado.
     */
    APROVADO(7, "Aprovado", new HashSet<>(), false, true),

    /**
     * Etapa onde o processo foi recusado.
     */
    RECUSADO(8, "Recusado", new HashSet<>(), false, true),

    /**
     * Etapa de erro para situações em que a etapa fornecida é inexistente.
     */
    ERRO(99, "Etapa inexistente", new HashSet<>(), false, true);


    /**
     * Construtor da enumeração para etapas que não permitem votação e não são finais.
     *
     * @param key o identificador único da etapa.
     * @param description a descrição da etapa.
     * @param nextSteps os próximos passos permitidos a partir dessa etapa.
     */
    StepTypeEnum(Integer key, String description, Set<Integer> nextSteps) {
        this.key = key;
        this.description = description;
        this.nextSteps = nextSteps;
        this.permitVote = false;
        this.finalStatus = false;
    }


    /**
     * Construtor da enumeração para etapas que permitem definir se há votação e se são etapas finais.
     *
     * @param key o identificador único da etapa.
     * @param description a descrição da etapa.
     * @param nextSteps os próximos passos permitidos a partir dessa etapa.
     * @param permitVote indica se a etapa permite votação.
     * @param finalStatus indica se a etapa é final.
     */
    StepTypeEnum(Integer key, String description, Set<Integer> nextSteps, boolean permitVote, boolean finalStatus) {
        this.key = key;
        this.description = description;
        this.nextSteps = nextSteps;
        this.permitVote = permitVote;
        this.finalStatus = finalStatus;
    }

    @Getter
    private final Integer key;
    @Getter
    private final String description;
    private final Set<Integer> nextSteps;
    @Getter
    private final boolean finalStatus;
    @Getter
    private final boolean permitVote;

    /**
     * Obtém o tipo de etapa correspondente à chave fornecida.
     *
     * <p>Se a chave não corresponder a nenhuma etapa existente, retorna {@link StepTypeEnum#ERRO}.
     *
     * @param key a chave da etapa.
     * @return o tipo de etapa correspondente à chave, ou {@link StepTypeEnum#ERRO} se a chave for inválida.
     */
    public static StepTypeEnum getByKey(Integer key) {
        return Arrays.stream(values()).filter(stepTypeEnum -> stepTypeEnum.getKey().equals(key)).findFirst().orElse(ERRO);
    }

    /**
     * Obtém o conjunto de próximos passos possíveis a partir da etapa atual.
     *
     * <p>O método mapeia os identificadores das próximas etapas permitidas para seus
     * respectivos valores na enumeração {@link StepTypeEnum}.
     *
     * @return um conjunto imutável contendo os próximos passos permitidos a partir desta etapa.
     */
    public Set<StepTypeEnum> getNextSteps() {
        return this.nextSteps.stream().map(StepTypeEnum::getByKey).collect(Collectors.toUnmodifiableSet());
    }


}
