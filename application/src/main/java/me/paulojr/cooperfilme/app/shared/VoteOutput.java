package me.paulojr.cooperfilme.app.shared;

import me.paulojr.cooperfilme.domain.script.core.vo.VoteVo;

import java.util.Date;

public record VoteOutput(
        String type,
        UserOutput user,
        Date voteDate
) {


    public static VoteOutput from(VoteVo voteVo) {
        final UserOutput userOutput = voteVo.userID() != null ? new UserOutput(voteVo.userID().getValue().toString(), voteVo.userName()) : null;
        return new VoteOutput(voteVo.type().getDescription(), userOutput, voteVo.voteDate());

    }
}
