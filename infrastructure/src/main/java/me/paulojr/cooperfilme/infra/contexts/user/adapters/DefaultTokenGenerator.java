package me.paulojr.cooperfilme.infra.contexts.user.adapters;

import me.paulojr.cooperfilme.domain.user.adapters.TokenGenerator;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.infra.config.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTokenGenerator implements TokenGenerator {

    private final JwtService jwtService;

    @Override
    public String generateToken(User user) {
        return this.jwtService.generateToken(new UserAuthAdapter(user));
    }
}
