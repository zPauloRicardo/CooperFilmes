package me.paulojr.cooperfilme.domain.script.core.vo;

import me.paulojr.cooperfilme.domain.script.core.enums.StepTypeEnum;
import me.paulojr.cooperfilme.domain.shared.utils.InstantUtils;
import me.paulojr.cooperfilme.domain.user.entity.UserID;

import java.util.Date;
import java.util.UUID;


/**
 * Representa uma etapa no processo do 'Script'.
 * Utiliza o tipo {@link StepTypeEnum} para definir o tipo da etapa e inclui a data em que a etapa foi registrada.
 *
 * @param id            o identificador da etapa.
 * @param userId        o identificador do usuário que registrou a etapa.
 * @param userName      o nome do usuário que registrou a etapa.
 * @param stepDate      a data em que a etapa foi registrada.
 * @param type          o tipo da etapa, definido pelo enum {@link StepTypeEnum}.
 * @param justification a justificativa fornecida para a etapa, se houver.
 */
public record StepVo(
        UUID id,
        UserID userId,
        String userName,
        Date stepDate,
        StepTypeEnum type,
        String justification

) {

    /**
     * Construtor do {@link StepVo} com data atual.
     *
     * @param userId        o identificador do usuário que registrou a etapa.
     * @param userName      o nome do usuário que registrou a etapa.
     * @param type          o tipo da etapa.
     * @param justification a justificativa fornecida para a etapa.
     */
    public StepVo(UserID userId, String userName, StepTypeEnum type, String justification) {
        this(null, userId, userName, Date.from(InstantUtils.now()), type, justification);
    }
}
