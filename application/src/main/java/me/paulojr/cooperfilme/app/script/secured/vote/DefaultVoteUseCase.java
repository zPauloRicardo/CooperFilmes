package me.paulojr.cooperfilme.app.script.secured.vote;

import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.enums.VoteTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.shared.exceptions.NotFoundException;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.domain.user.gateway.UserGateway;

import java.util.function.Supplier;

public class DefaultVoteUseCase extends VoteUseCase {

    private final UserGateway userGateway;
    private final ScriptGateway scriptGateway;

    public DefaultVoteUseCase(UserGateway userGateway, ScriptGateway scriptGateway) {
        this.userGateway = userGateway;
        this.scriptGateway = scriptGateway;
    }

    private Supplier<NotFoundException> notFound(final ScriptID scriptId) {
        return () -> NotFoundException.with(Script.class, scriptId);
    }

    private Supplier<NotFoundException> notFound(final UserID userID) {
        return () -> NotFoundException.with(User.class, userID);
    }


    @Override
    public ScriptOutput execute(VoteCommand anIn) {
        final ScriptID scriptID = ScriptID.from(anIn.scriptId());
        final UserID userID = UserID.from(anIn.userID());

        final Script script = this.scriptGateway.findById(scriptID).orElseThrow(notFound(scriptID));
        final User user = this.userGateway.findById(userID).orElseThrow(notFound(userID));

        final VoteTypeEnum type = VoteTypeEnum.getByKey(anIn.type());

        script.vote(user, type);

        return ScriptOutput.from(this.scriptGateway.update(script));
    }
}
