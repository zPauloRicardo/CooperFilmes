package me.paulojr.cooperfilme.app.script.secured.step;

import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.entity.Script;
import me.paulojr.cooperfilme.domain.script.core.entity.ScriptID;
import me.paulojr.cooperfilme.domain.script.core.enums.StepTypeEnum;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.shared.exceptions.DomainException;
import me.paulojr.cooperfilme.domain.shared.exceptions.NotFoundException;
import me.paulojr.cooperfilme.domain.shared.validation.handler.Notification;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.domain.user.gateway.UserGateway;

import java.util.function.Supplier;

public class DefaultAddStepUseCase extends AddStepUseCase {

    private final UserGateway userGateway;
    private final ScriptGateway scriptGateway;

    public DefaultAddStepUseCase(UserGateway userGateway, ScriptGateway scriptGateway) {
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
    public ScriptOutput execute(AddStepCommand anIn) {
        final ScriptID scriptID = ScriptID.from(anIn.scriptId());
        final UserID userID = UserID.from(anIn.userID());

        final Script script = this.scriptGateway.findById(scriptID).orElseThrow(notFound(scriptID));
        final User user = this.userGateway.findById(userID).orElseThrow(notFound(userID));

        final Notification errors = Notification.create();

        final StepTypeEnum type = StepTypeEnum.getByKey(anIn.type());

        try {
            switch (type) {
                case AGUARDANDO_ANALISE, APROVADO, RECUSADO, EM_APROVACAO -> cantMoveToStep(errors, type);
                case EM_ANALISE -> script.assumeAnalysis(user);
                case AGUARDANDO_REVISAO -> script.sendToReview(user, anIn.text());
                case EM_REVISAO -> script.assumeReview(user);
                case AGUARDANDO_APROVACAO -> script.sendToApprove(user, anIn.text());
                case ERRO -> errors.append(type.getDescription());
            }
        } catch (DomainException ex) {
            ex.getErrors().forEach(errors::append);
        }

        errors.throwIfHasError();

        return ScriptOutput.from(this.scriptGateway.update(script));
    }

    private static void cantMoveToStep(Notification errors, StepTypeEnum stepTypeEnum) {
        errors.append(String.format("Não é possivel alterar roteiros para etapa de %s.", stepTypeEnum.getDescription()));
    }
}
