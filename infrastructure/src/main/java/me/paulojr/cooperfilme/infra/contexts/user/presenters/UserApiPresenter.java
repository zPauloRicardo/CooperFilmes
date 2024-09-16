package me.paulojr.cooperfilme.infra.contexts.user.presenters;

import me.paulojr.cooperfilme.infra.contexts.user.models.UserResponse;
import me.paulojr.cooperfilme.domain.user.entity.User;

public interface UserApiPresenter {

    static UserResponse present(final User user) {
        if (user == null) return null;
        return new UserResponse(
                user.getId().getValue().toString(),
                user.getName(),
                user.getProfile().getDescription(),
                user.getEmail(),
                user.getCreationDate()
        );
    }
}
