package me.paulojr.cooperfilme.domain.script.core.vo;

import me.paulojr.cooperfilme.domain.script.core.enums.VoteTypeEnum;
import me.paulojr.cooperfilme.domain.shared.utils.InstantUtils;
import me.paulojr.cooperfilme.domain.user.entity.UserID;

import java.util.Date;
import java.util.UUID;


/**
 * Representa um voto registrado por um usuário.
 * Utiliza o tipo {@link VoteTypeEnum} para representar o tipo de voto e inclui a data em que o voto foi registrado.
 *
 * @param id       o identificador do voto.
 * @param userID   o identificador do usuário que fez o voto.
 * @param userName o nome do usuário que fez o voto.
 * @param type     o tipo de voto realizado, definido pelo enum {@link VoteTypeEnum}.
 * @param voteDate a data em que o voto foi registrado.
 */
public record VoteVo(
        UUID id,
        UserID userID,
        String userName,
        VoteTypeEnum type,
        Date voteDate

) {
    /**
     * Construtor para um {@link VoteVo} com data e hora atual.
     *
     * @param userID   o identificador do usuário que fez o voto.
     * @param userName o nome do usuário que fez o voto.
     * @param type     o tipo de voto realizado.
     */
    public VoteVo(UserID userID, String userName, VoteTypeEnum type) {
        this(null, userID, userName, type, Date.from(InstantUtils.now()));
    }
}
