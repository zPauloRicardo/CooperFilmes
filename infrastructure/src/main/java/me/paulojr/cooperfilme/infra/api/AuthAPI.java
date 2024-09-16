package me.paulojr.cooperfilme.infra.api;

import me.paulojr.cooperfilme.infra.contexts.user.models.UserResponse;
import me.paulojr.cooperfilme.infra.api.controllers.ExceptionController;
import me.paulojr.cooperfilme.infra.contexts.user.models.LoginRequest;
import me.paulojr.cooperfilme.infra.contexts.user.models.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
@Tag(name = "Auth API", description = "API de autenticação dos usuários.")
public interface AuthAPI {

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            operationId = "authLogin",
            summary = "Autenticação do usuário",
            description = "Autenticação do usuário com base nas credenciais informadas.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado"),
                    @ApiResponse(responseCode = "400", description = "Requesição inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
            },
            method = "POST",
            tags = "Auth API"
    )
    ResponseEntity<LoginResponse> authLogin(@RequestBody LoginRequest request);

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            operationId = "getByToken",
            summary = "Obter usuário",
            description = "Obter usuário autenticado pelo token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
                    @ApiResponse(responseCode = "400", description = "Requesição inválida", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ExceptionController.CustomErrorResponse.class)))
            },
            security = @SecurityRequirement(name = "bearerAuth"),
            method = "GET",
            tags = "Auth API"
    )
    ResponseEntity<UserResponse> getByToken();

}
