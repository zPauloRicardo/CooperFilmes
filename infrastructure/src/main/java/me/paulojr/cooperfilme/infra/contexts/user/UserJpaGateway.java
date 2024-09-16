package me.paulojr.cooperfilme.infra.contexts.user;

import me.paulojr.cooperfilme.infra.contexts.user.persistence.UserJpaEntity;
import me.paulojr.cooperfilme.infra.contexts.user.persistence.UserRepository;
import me.paulojr.cooperfilme.domain.user.entity.User;
import me.paulojr.cooperfilme.domain.user.entity.UserID;
import me.paulojr.cooperfilme.domain.user.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static me.paulojr.cooperfilme.infra.utils.SpecificationUtils.like;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserJpaGateway implements UserGateway {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findById(UserID anUserID) {
        return this.userRepository.findById(anUserID.getValue()).map(UserJpaEntity::toDomain);
    }


    @Override
    public boolean existsByEmail(String anEmail) {
        return this.userRepository.existsByEmail(anEmail);
    }


    @Override
    public Optional<User> findByEmail(String anEmail) {
        return this.userRepository.findByEmail(anEmail).map(UserJpaEntity::toDomain);
    }

}
