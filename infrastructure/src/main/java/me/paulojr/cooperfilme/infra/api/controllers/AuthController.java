package me.paulojr.cooperfilme.infra.api.controllers;

import me.paulojr.cooperfilme.infra.api.AuthAPI;
import me.paulojr.cooperfilme.infra.contexts.user.adapters.UserAuthAdapter;
import me.paulojr.cooperfilme.infra.contexts.user.models.LoginRequest;
import me.paulojr.cooperfilme.infra.contexts.user.models.LoginResponse;
import me.paulojr.cooperfilme.infra.contexts.user.models.UserResponse;
import me.paulojr.cooperfilme.infra.contexts.user.persistence.UserJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.user.persistence.UserRepository;
import me.paulojr.cooperfilme.infra.contexts.user.presenters.UserApiPresenter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.paulojr.cooperfilme.domain.user.adapters.TokenGenerator;
import me.paulojr.cooperfilme.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthController implements AuthAPI {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<LoginResponse> authLogin(LoginRequest request) {
        final Optional<UserJpaEntity> userOptional = this.userRepository.findByEmail(request.email()).or(() -> this.userRepository.findByEmail(request.email()));
        if (userOptional.isEmpty()) throw new BadCredentialsException("Usuário não encontrado.");
        final User user = userOptional.get().toDomain();
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),
                request.password()));

        return ResponseEntity.ok(new LoginResponse(this.tokenGenerator.generateToken(new UserAuthAdapter(user)),
                UserApiPresenter.present(user)));
    }

    @Override
    public ResponseEntity<UserResponse> getByToken() {
        return ResponseEntity.ok(
                UserApiPresenter.present((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        );
    }


}
