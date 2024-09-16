package me.paulojr.cooperfilme.infra.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.paulojr.cooperfilme.app.shared.ScriptOutput;
import me.paulojr.cooperfilme.domain.shared.search.Pagination;
import me.paulojr.cooperfilme.infra.api.controllers.ExceptionController;
import me.paulojr.cooperfilme.infra.contexts.script.core.models.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/script")
@Tag(name = "Script API", description = "API de gerenciamento de roteiros.")
public interface ScriptAPI {


    @PostMapping(
            value = "/public/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "createScript",
            summary = "Cria um script (publico)",
            description = "Cria um script e um cliente a partir das informações ou atualiza cliente existente a partir do email.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Roteiro criado"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = CreateScriptRequest.class))),
            method = "POST"

    )
    ResponseEntity<CreateScriptResponse> createScript(@RequestBody CreateScriptRequest request);


    @GetMapping(
            value = "/public/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "getPublicScript",
            summary = "Consulta roteiro (publico)",
            description = "Consulta roteiros associados a um email, se informar um id de roteiro o resultado será unico.",
            parameters = {
                    @Parameter(name = "page", description = "Página a ser consultada", in = ParameterIn.QUERY, example = "0"),
                    @Parameter(name = "email", description = "Email para ser consultado", in = ParameterIn.QUERY, example = "email@email.com"),
                    @Parameter(name = "scriptId", description = "ID de roteiro a ser consultado", in = ParameterIn.QUERY, example = "c971869b-2dd3-4934-beac-172a9a229735")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roteiros retornados"),
                    @ApiResponse(responseCode = "400", description = "Consulta inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            method = "GET"

    )
    ResponseEntity<Pagination<GetScriptResponse>> getPublicScript(@RequestParam(defaultValue = "0") Integer page, @RequestParam String email, @RequestParam(required = false) String scriptId);

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "getScript",
            summary = "Consulta roteiro",
            description = "Consulta roteiro pelo id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roteiro retornado"),
                    @ApiResponse(responseCode = "400", description = "Consulta inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Roteiro não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            parameters = @Parameter(name = "id", description = "ID do roteiro a ser consultado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
            method = "GET",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    ResponseEntity<ScriptOutput> getScript(@PathVariable String id);

    @PatchMapping(
            value = "/{id}/addStep",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "addStep",
            summary = "Lançar etapa",
            description = "Lança uma etapa no roteiro indicado. Necessario enviar o id da etapa e, quando necessario, a justificativa.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Etapa lançada"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Objeto não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            parameters = @Parameter(name = "id", description = "ID do roteiro a ser alterado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
            method = "PATCH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = AddStepRequest.class))),
            security = @SecurityRequirement(name = "bearerAuth")
    )
    ResponseEntity<ScriptOutput> addStep(@PathVariable String id, @RequestBody AddStepRequest voteRequest);


    @PatchMapping(
            value = "/{id}/addVote",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "addVote",
            summary = "Adicionar voto",
            description = "Adiciona um voto roteiro indicado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Etapa lançada"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Objeto não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            parameters = @Parameter(name = "id", description = "ID do roteiro a ser alterado", in = ParameterIn.PATH, example = "c971869b-2dd3-4934-beac-172a9a229735", required = true),
            method = "PATCH",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = AddVoteRequest.class))),
            security = @SecurityRequirement(name = "bearerAuth")
    )
    ResponseEntity<ScriptOutput> addVote(@PathVariable String id, @RequestBody AddVoteRequest addVoteRequest);



    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "searchScript",
            summary = "Pesquisa Roteiros",
            description = "Pesquisa roteiros associados a um email, status ou data de criação.",
            parameters = {
                    @Parameter(name = "page", description = "Página a ser consultada", in = ParameterIn.QUERY, example = "0"),
                    @Parameter(name = "email", description = "Email para ser consultado", in = ParameterIn.QUERY, example = "email@email.com"),
                    @Parameter(name = "stepId", description = "ID do tipo de status a ser consultado", in = ParameterIn.QUERY, example = "7"),
                    @Parameter(name = "creationDate", description = "Data de criação dd-MM-yyyy", in = ParameterIn.QUERY, example = "19-12-2020")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Roteiros retornados"),
                    @ApiResponse(responseCode = "400", description = "Consulta inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
            },
            method = "GET",
            security = @SecurityRequirement(name = "bearerAuth")

    )
    ResponseEntity<Pagination<ScriptOutput>> searchScript(@RequestParam(defaultValue = "0") Integer page, @RequestParam(required = false) String email, @RequestParam(required = false) Integer stepId, @RequestParam(required = false) String creationDate);

}

