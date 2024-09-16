package me.paulojr.cooperfilme.infra.config.auth;

import me.paulojr.cooperfilme.domain.shared.utils.UUIDUtils;
import me.paulojr.cooperfilme.infra.contexts.user.adapters.UserAuthAdapter;
import me.paulojr.cooperfilme.infra.contexts.user.persistence.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserAuthAdapter getByUUID(String uuid) {
        return userRepository.findById(UUIDUtils.getFromString(uuid)).map(userJpa -> new UserAuthAdapter(userJpa.toDomain())).orElseThrow(() -> new UsernameNotFoundException("Token inválido."));
    }
}
