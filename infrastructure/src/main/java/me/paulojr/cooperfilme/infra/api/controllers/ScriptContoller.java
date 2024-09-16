package me.paulojr.cooperfilme.infra.api.controllers;


import lombok.RequiredArgsConstructor;
import me.paulojr.cooperfilme.app.script.global.create.CreateScriptCommand;
import me.paulojr.cooperfilme.app.script.global.create.CreateScriptOutput;
import me.paulojr.cooperfilme.app.script.global.create.CreateScriptUseCase;
import me.paulojr.cooperfilme.app.script.global.get.GetGlobalScriptStatusCommand;
import me.paulojr.cooperfilme.app.script.global.get.GetGlobalScriptStatusOutput;
import me.paulojr.cooperfilme.app.script.global.get.GetGlobalScriptStatusUseCase;
import me.paulojr.cooperfilme.app.script.secured.get.GetScriptCommand;
import me.paulojr.cooperfilme.app.script.secured.get.GetScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.search.SearchScriptUseCase;
import me.paulojr.cooperfilme.app.script.secured.step.AddStepCommand;
import me.paulojr.cooperfilme.app.script.secured.step.AddStepUseCase;
import me.paulojr.cooperfilme.app.script.secured.vote.VoteCommand;
import me.paulojr.cooperfilme.app.script.secured.vote.VoteUseCase;
import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.script.core.search.ScriptSearchCommand;
import me.paulojr.cooperfilme.domain.shared.exceptions.DomainException;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;
import me.paulojr.cooperfilme.domain.shared.validation.Error;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.infra.contexts.script.core.models.*;
import me.paulojr.cooperfilme.infra.api.ScriptAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

@RestController
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class ScriptContoller implements ScriptAPI {

    private final VoteUseCase voteUseCase;
    private final AddStepUseCase addStepUseCase;
    private final SearchScriptUseCase searchScriptUseCase;
    private final CreateScriptUseCase createScriptUseCase;
    private final GetScriptUseCase getScriptUseCase;
    private final GetGlobalScriptStatusUseCase getGlobalScriptStatusUseCase;


    @Override
    public ResponseEntity<CreateScriptResponse> createScript(CreateScriptRequest request) {
        CreateScriptOutput execute = this.createScriptUseCase.execute(
                new CreateScriptCommand(
                        request.name(),
                        request.phone(),
                        request.email(),
                        request.text()
                )
        );

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(execute.id())
                .toUri();


        return ResponseEntity
                .created(uri)
                .body(new CreateScriptResponse(execute.id(), execute.status(), execute.email()));
    }

    @Override
    public ResponseEntity<Pagination<GetScriptResponse>> getPublicScript(Integer page, String email, String scriptId) {
        final Pagination<GetGlobalScriptStatusOutput> execute = this.getGlobalScriptStatusUseCase.execute(
                new GetGlobalScriptStatusCommand(email, scriptId, page)
        );
        return ResponseEntity.ok(
                Pagination.of(
                        page,
                        execute.perPage(),
                        execute.total(),
                        execute.items().stream()
                                .map(i -> new GetScriptResponse(i.id(), i.status(), i.justification()))
                                .toList())
        );
    }

    @Override
    public ResponseEntity<ScriptOutput> getScript(String id) {
        final ScriptOutput execute = this.getScriptUseCase.execute(new GetScriptCommand(id));
        return ResponseEntity.ok(execute);
    }

    @Override
    public ResponseEntity<ScriptOutput> addStep(String id, AddStepRequest voteRequest) {
        final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ScriptOutput execute = this.addStepUseCase.execute(
                new AddStepCommand(id, principal.getId().getValue().toString(), voteRequest.type(), voteRequest.text())
        );
        return ResponseEntity.ok(execute);
    }

    @Override
    public ResponseEntity<ScriptOutput> addVote(String id, AddVoteRequest addVoteRequest) {
        final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final ScriptOutput execute = this.voteUseCase.execute(
                new VoteCommand(id, principal.getId().getValue().toString(), addVoteRequest.type())
        );
        return ResponseEntity.ok(execute);
    }

    @Override
    public ResponseEntity<Pagination<ScriptOutput>> searchScript(Integer page, String email, Integer stepId, String creationDate) {
        final Date date;
        if (creationDate != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate localDate = LocalDate.parse(creationDate, formatter);
                date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } catch (DateTimeParseException ex) {
                throw DomainException.with(new Error("Data em formato inválido"));
            }
        } else {
            date = null;
        }


        final Pagination<ScriptOutput> execute = this.searchScriptUseCase.execute(
                new ScriptSearchCommand(page, email, stepId, date)
        );
        return ResponseEntity.ok(execute);
    }
}
