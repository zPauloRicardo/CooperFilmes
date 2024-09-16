package me.paulojr.cooperfilme.infra.config.usecases;

import lombok.RequiredArgsConstructor;
import me.paulojr.cooperfilme.app.script.global.create.CreateScriptUseCase;
import me.paulojr.cooperfilme.app.script.global.create.DefaultCreateScriptUseCase;
import me.paulojr.cooperfilme.app.script.global.get.DefaultGetGlobalScriptStatusUseCase;
import me.paulojr.cooperfilme.app.script.global.get.GetGlobalScriptStatusUseCase;
import me.paulojr.cooperfilme.app.script.secured.get.DefaultGetScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.get.GetScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.search.DefaultSearchScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.search.SearchScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.step.AddStepUseCase;
import me.paulojr.cooperfilme.app.script.secured.step.DefaultAddStepUseCase;
import me.paulojr.cooperfilme.app.script.secured.vote.DefaultVoteUseCase;
import me.paulojr.cooperfilme.app.script.secured.vote.VoteUseCase;
import me.paulojr.cooperfilme.domain.script.core.gateway.ScriptGateway;
import me.paulojr.cooperfilme.domain.script.customer.gateway.CustomerGateway;
import me.paulojr.cooperfilme.domain.user.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UseCasesConfig {

    private final CustomerGateway customerGateway;
    private final UserGateway userGateway;
    private final ScriptGateway scriptGateway;

    @Bean
    public VoteUseCase voteUseCase() {
        return new DefaultVoteUseCase(this.userGateway, this.scriptGateway);
    }

    @Bean
    public AddStepUseCase addStepUseCase() {
        return new DefaultAddStepUseCase(this.userGateway, this.scriptGateway);
    }

    @Bean
    public SearchScriptUseCase searchScriptUseCase() {
        return new DefaultSearchScriptUseCase(this.scriptGateway);
    }

    @Bean
    public GetScriptUseCase getScriptUseCase() {
        return new DefaultGetScriptUseCase(this.scriptGateway);
    }

    @Bean
    public CreateScriptUseCase createScriptUseCase() {
        return new DefaultCreateScriptUseCase(this.scriptGateway, this.customerGateway);
    }

    @Bean
    public GetGlobalScriptStatusUseCase getGlobalScriptStatusUseCase() {
        return new DefaultGetGlobalScriptStatusUseCase(this.scriptGateway, this.customerGateway);
    }

}
